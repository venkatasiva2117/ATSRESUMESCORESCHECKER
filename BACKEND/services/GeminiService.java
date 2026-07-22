package com.AI_Powered_Resume_JobMatcher.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GeminiService {

	private final WebClient webClient;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${gemini.api.key}")
	private String apiKey;

	@Value("${gemini.api.model}")
	private String model;

	public GeminiService(WebClient.Builder webClientBuilder,
			@Value("${gemini.api.base-url}") String baseUrl) {
		this.webClient = webClientBuilder.baseUrl(baseUrl).build();
	}

	public JsonNode getMatchAnalysis(String resumeText, String jobDescription) {
		String prompt = buildPrompt(resumeText, jobDescription);
		String rawResponse = callGroq(prompt);
		String modelText = extractModelText(rawResponse);
		return parseJsonSafely(modelText);
	}

	private JsonNode parseJsonSafely(String modelText) {
		String cleaned = modelText
				.trim()
				.replaceAll("^```json\\s*", "")
				.replaceAll("^```\\s*", "")
				.replaceAll("```$", "")
				.trim();

		try {
			return objectMapper.readTree(cleaned);
		} catch (Exception e) {
			throw new RuntimeException(
					"Groq did not return valid JSON. Raw output: " + modelText, e);
		}
	}

	private String extractModelText(String rawResponse) {
		try {
			JsonNode root = objectMapper.readTree(rawResponse);
			JsonNode textNode = root
					.path("choices").path(0)
					.path("message").path("content");

			if (textNode.isMissingNode()) {
				throw new RuntimeException("Unexpected Groq response format: " + rawResponse);
			}
			return textNode.asText();
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse Groq response envelope.", e);
		}
	}

	private String callGroq(String prompt) {
		Map<String, Object> requestBody = Map.of(
				"model", model,
				"messages", List.of(
						Map.of("role", "user", "content", prompt)
				),
				"temperature", 0.2,
				"max_tokens", 1024
		);

		try {
			return webClient.post()
					.uri("/chat/completions")
					.header("Authorization", "Bearer " + apiKey)
					.bodyValue(requestBody)
					.retrieve()
					.bodyToMono(String.class)
					.timeout(Duration.ofSeconds(30))
					.block();
		} catch (WebClientResponseException e) {
			throw new RuntimeException(
					"Groq API call failed: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
		}
	}

	private String buildPrompt(String resumeText, String jobDescription) {
		return """
                You are an ATS and technical recruiter expert. Compare the following resume
                against the job description and respond ONLY with valid JSON, no markdown
                fences, no extra commentary, in exactly this format:

                {
                  "matchPercent": <integer 0-100>,
                  "matchedSkills": [<skills present in both resume and job description>],
                  "missingSkills": [<skills required by the job description but missing from the resume>],
                  "suggestions": [<3 to 5 specific, actionable resume improvements>]
                }

                Resume:
                %s

                Job Description:
                %s
                """.formatted(resumeText, jobDescription);
	}

}