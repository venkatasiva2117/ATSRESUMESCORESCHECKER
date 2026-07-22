package com.AI_Powered_Resume_JobMatcher.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.AI_Powered_Resume_JobMatcher.Entity.MatchResponse;
import com.fasterxml.jackson.databind.JsonNode;

@Service

public class MatchService {

	
	private final ResumeParserService resumeParaserService;
	private final GeminiService geminiService;
	

	public MatchService(ResumeParserService resumeParaserService, GeminiService geminiService) {
		this.resumeParaserService = resumeParaserService;
		this.geminiService = geminiService;	
    }
	
	public MatchResponse getMatchResult(MultipartFile resumeFile, String jobDescription) throws IOException {
		String resumeText = resumeParaserService.extractText(resumeFile);
		
		
		// 1. Extract plain text from the uploaded resume
		if(resumeText == null || resumeText.isBlank()) {
			 throw new IllegalArgumentException("Could not extract text from resume file");
		}
		
		if(jobDescription == null || jobDescription.isBlank()) {
			 throw new IllegalArgumentException("Job description must not be empty");
		}
		
		JsonNode analysis = geminiService.getMatchAnalysis(resumeText, jobDescription);
		
		
		
		
		return toMatchResponse(analysis);
		
		
		
	}

	private MatchResponse toMatchResponse(JsonNode root) {

		int matchPercent = root.path("matchPercent").asInt();
		List<String> matchedSkills = toStringList(root.path("matchedSkills"));
		List<String> missingSkills = toStringList(root.path("missingSkills"));
		List<String> suggestions = toStringList(root.path("suggestions"));
		return new MatchResponse(matchPercent,matchedSkills,missingSkills,suggestions);
	}

	private List<String> toStringList(JsonNode arrayNode) {

		List<String> result = new ArrayList<>();
		if(arrayNode.isArray()) {
			arrayNode.forEach(node -> result.add(node.asText()));
		}
		return result;
	}
}
