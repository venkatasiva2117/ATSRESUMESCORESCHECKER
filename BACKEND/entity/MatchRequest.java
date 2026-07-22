package com.AI_Powered_Resume_JobMatcher.Entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;


public class MatchRequest {
	
	@NotNull(message = "Resume file is required")
	private MultipartFile resume;
	
	@NotBlank(message = "Job description cannot be empty")
	private String jobDescription;
	
	public MatchRequest() {
		
	}
	

	public MatchRequest(MultipartFile resume,String jobDescription) {
		this.resume = resume;
		this.jobDescription = jobDescription;
	}



	public MultipartFile getResume() {
		return resume;
	}

	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	
	
}
