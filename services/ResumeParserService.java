package com.AI_Powered_Resume_JobMatcher.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeParserService {

	private static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5 MB

	public String extractText(MultipartFile file) throws IOException{
		if(file == null || file.isEmpty()) {
			throw new IllegalArgumentException("Resume file is missing or empty.");	
		}
		if(file.getSize() > MAX_FILE_SIZE_BYTES) {
			throw new IllegalArgumentException("Resume file exceeds the 5MB size limit.");
		}
		String fileName = file.getOriginalFilename();
		if(fileName == null || fileName.isBlank()) {
			throw  new IllegalArgumentException("Uploaded file has no name.");
		}

		String lowerName = fileName.toLowerCase(Locale.ROOT);


		String extractedText;
		if(lowerName.endsWith(".pdf")) {
			extractedText = extractFromPdf(file);
		}else if(lowerName.endsWith(".docx")) {
			extractedText = extractFromDocx(file);
		}else {
			throw new IllegalArgumentException("Unsupported file type: " + fileName + ". Please upload a .pdf or .docx file.");
		}
		
		if(extractedText == null || extractedText.isBlank()) {
			throw new IllegalArgumentException(
                    "Could not extract any text from the resume. The file may be scanned/image-based or corrupted.");
		}

		

		return cleanText(extractedText);	
	}

	private String extractFromDocx(MultipartFile file) throws IOException {

		try(InputStream inputStream = file.getInputStream();
				XWPFDocument document = new XWPFDocument(inputStream)){
		        XWPFWordExtractor extractor = new XWPFWordExtractor(document);
		        return extractor.getText();
		}
	}

	private String extractFromPdf(MultipartFile file) throws IOException {
		try(InputStream inputStream = file.getInputStream();
				PDDocument document = PDDocument.load(inputStream)){
			if(document.isEncrypted()) {
				throw new IllegalArgumentException("Password-protected PDFs are not supported.");
			}

			PDFTextStripper stripper = new PDFTextStripper();
			stripper.setSortByPosition(true);
			return stripper.getText(document);
		}
	}
	
	

	private String cleanText(String rawText) {
		return rawText.replaceAll("\\r\\n?", "\n")
				.replaceAll("[ \\t]+", " ")
				.replaceAll("\\n{3,}", "\n\n")
				.trim();
	}

}
