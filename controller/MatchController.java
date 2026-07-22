package com.AI_Powered_Resume_JobMatcher.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.AI_Powered_Resume_JobMatcher.Entity.MatchResponse;
import com.AI_Powered_Resume_JobMatcher.service.MatchService;


@RestController
@RequestMapping("/match")
@CrossOrigin(origins  = "*")
public class MatchController {
	
	private final MatchService matchservice;
	
	public MatchController(MatchService matchservice) {
		this.matchservice = matchservice;
	}
	
	@PostMapping()
	public ResponseEntity<?> matchResumeToJob(
			 @RequestParam("jobDescription") String jobDescription, @RequestParam("resume") MultipartFile resumeFile
			) throws IOException{
		try {
			MatchResponse response = matchservice.getMatchResult(resumeFile, jobDescription);
			return ResponseEntity.ok(response);
		}
		catch(IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
		catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("Failed to read resume file: " + ex.getMessage());

        } catch (Exception ex) {
            //return  ex.printStackTrace();   // Prints the full error in the console

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getClass().getName() + " : " + ex.getMessage());
        }
		
	}
	

}
