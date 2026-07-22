package com.AI_Powered_Resume_JobMatcher.Entity;

import java.util.List;

public class MatchResponse {

	private int matchPercentage;
	private List<String> matchedSkills;
	private List<String> missingSkills;
	private List<String> suggestions;
	
	public MatchResponse() {
		
	}
	
	public MatchResponse(int matchPercentage,List<String> matchedSkills,List<String> missingSkills,List<String> suggestions) {
		this.matchPercentage = matchPercentage;
		this.matchedSkills = matchedSkills;
		this.missingSkills = missingSkills;
		this.suggestions = suggestions;
	}

	public int getMatchPercentage() {
		return matchPercentage;
	}

	public void setMatchPercentage(int matchPercentage) {
		this.matchPercentage = matchPercentage;
	}

	public List<String> getMatchedSkills() {
		return matchedSkills;
	}

	public void setMatchedSkills(List<String> matchedSkills) {
		this.matchedSkills = matchedSkills;
	}

	public List<String> getMissingSkills() {
		return missingSkills;
	}

	public void setMissingSkills(List<String> missingSkills) {
		this.missingSkills = missingSkills;
	}

	public List<String> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<String> suggestions) {
		this.suggestions = suggestions;
	}
	
	
	
	
}
