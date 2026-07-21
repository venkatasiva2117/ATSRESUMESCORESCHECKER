# AI Resume / Job Matcher — Backend

Spring Boot REST API that analyzes how well a resume matches a given job description using Google's Gemini API. Extracts text from uploaded resumes (PDF/DOCX), sends it along with the job description to Gemini with a structured prompt, and returns a match score, matched/missing skills, and improvement suggestions as JSON.

## Tech Stack

- **Java 21**
- **Spring Boot 3.4**
- **Spring Web** (REST APIs)
- **Apache PDFBox** — PDF text extraction
- **Apache POI** — DOCX text extraction
- **WebClient** — HTTP calls to Gemini API
- **Google Gemini API** (`gemini-1.5-flash`) — AI-powered resume analysis
- **Maven** — build tool

## Features

- Upload a resume (PDF or DOCX) via multipart form-data
- Paste any job description as plain text
- Returns:
  - Match percentage (0–100)
  - List of matched skills
  - List of missing skills required by the job
  - Actionable suggestions to improve the resume
- Centralized error handling for invalid files, oversized uploads, and Gemini API failures

## API Endpoint

```
POST /match
Content-Type: multipart/form-data

Fields:
  resume          (file, required)  — PDF or DOCX
  jobDescription  (text, required)  — Job description as plain text
```

**Example success response:**
```json
{
  "matchPercent": 62,
  "matchedSkills": ["Java", "Spring Boot", "MySQL"],
  "missingSkills": ["TypeScript", "Docker", "Kubernetes"],
  "suggestions": [
    "Add TypeScript experience or a small project using it",
    "Mention any containerization exposure, even basic Docker usage"
  ]
}
```

**Example error response:**
```json
{
  "timestamp": "2026-07-20T18:12:03",
  "status": 400,
  "error": "Invalid Input",
  "message": "Unsupported file type"
}
```

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- A free Gemini API key from [Google AI Studio](https://aistudio.google.com/app/apikey)

### Setup

1. Clone the repository
   ```bash
   git clone <your-repo-url>
   cd backend
   ```

2. Set your Gemini API key as an environment variable (never hardcode it):
   ```bash
   # macOS/Linux
   export GEMINI_API_KEY=your_key_here

   # Windows (PowerShell)
   $env:GEMINI_API_KEY="your_key_here"
   ```

3. Run the application
   ```bash
   mvn spring-boot:run
   ```

4. The API will be available at `http://localhost:8080`

### Configuration

Key settings in `application.properties`:
```properties
gemini.api.key=${GEMINI_API_KEY}
gemini.api.base-url=https://generativelanguage.googleapis.com/v1beta
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

## Project Structure

```
src/main/java/com/yourname/matcher/
├── controller/       # REST endpoints
├── service/          # Resume parsing, Gemini API calls, orchestration
├── dto/              # Request/response models
├── exception/        # Centralized error handling
└── config/           # WebClient and other bean configuration
```

## Notes

- Uses Gemini's free tier — no cost to run for personal/portfolio use
- Resume text extraction supports `.pdf` and `.docx` only
- CORS is currently open (`@CrossOrigin(origins = "*")`) for local development; restrict this before any production deployment

## License

This project is for educational/portfolio purposes.
