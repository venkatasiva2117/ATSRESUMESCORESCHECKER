# AI-Powered Resume / Job Matcher

A full-stack web application that analyzes how well a resume matches a job description using Google's Gemini API. Upload a resume, paste a job description, and instantly get a match score, a breakdown of matched vs. missing skills, and AI-generated suggestions to improve your resume.

---

## Demo

*(Add a screenshot or GIF of the app here once deployed)*

```
<img width="1727" height="857" alt="image" src="https://github.com/user-attachments/assets/dcd5ebaf-4269-4b17-8391-ac6cfdd2fea8" />

```

---

## Tech Stack

**Backend**
- Java 21, Spring Boot 3.4
- Apache PDFBox / Apache POI — resume text extraction (PDF/DOCX)
- Spring WebClient — Gemini API integration
- Maven

**Frontend**
- React 18 + Vite
- Recharts — score visualization
- Fetch API

**AI**
- Google Gemini API (`gemini-1.5-flash`) — free tier

---

## Features

- Upload a resume (PDF or DOCX) and paste any job description
- AI-generated match analysis:
  - Match percentage (0–100)
  - Matched skills
  - Missing skills required by the job
  - Actionable resume improvement suggestions
- Clean, responsive dark-mode UI with a visual score gauge
- Centralized error handling on the backend for invalid files, oversized uploads, and API failures

---

## Architecture

```
┌─────────────┐   upload resume +    ┌──────────────────┐      HTTPS
│   React     │   paste JD           │   Spring Boot     │ ──────────────▶ Gemini API
│  Frontend   │ ────────────────────▶│    Backend        │
│ (Vite)      │◀──────────────────── │                    │
└─────────────┘   match score +      └──────────────────┘
                   suggestions (JSON)
```

1. User uploads a resume and pastes a job description in the React frontend
2. The Spring Boot backend extracts plain text from the resume file
3. Backend sends resume text + job description to Gemini with a structured, JSON-constrained prompt
4. Gemini returns a match score, skill comparison, and suggestions
5. Frontend renders the results as a score gauge, skills list, and suggestion cards

---

## Project Structure

```
ai-resume-job-matcher/
├── backend/            # Spring Boot REST API
│   ├── src/main/java/com/yourname/matcher/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── dto/
│   │   ├── exception/
│   │   └── config/
│   ├── src/main/resources/application.properties
│   └── README.md
├── frontend/            # React + Vite app
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   └── App.jsx
│   └── README.md
└── README.md            # you are here
```

---

## Getting Started

### Prerequisites

- Java 21+ and Maven 3.8+
- Node.js 18+
- A free Gemini API key from [Google AI Studio](https://aistudio.google.com/app/apikey)

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd ai-resume-job-matcher
```

### 2. Run the backend

```bash
cd backend

# Set your Gemini API key as an environment variable
export GEMINI_API_KEY=your_key_here      # macOS/Linux
$env:GEMINI_API_KEY="your_key_here"      # Windows PowerShell

mvn spring-boot:run
```
Backend runs on `http://localhost:8080`

### 3. Run the frontend

```bash
cd frontend
npm install
npm run dev
```
Frontend runs on `http://localhost:5173`

For more detail on each part, see [`backend/README.md`](./backend/README.md) and [`frontend/README.md`](./frontend/README.md).

---

## API Reference

```
POST /match
Content-Type: multipart/form-data

Fields:
  resume          (file, required) — PDF or DOCX
  jobDescription  (text, required) — plain text job description
```

**Example response:**
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

---

## Engineering Notes

- **Prompt design:** Gemini is instructed to respond only in strict JSON, which keeps parsing reliable instead of relying on free-text extraction.
- **Cost-conscious choice:** Uses Gemini's free tier and the lightweight `gemini-1.5-flash` model — sufficient for text analysis at this scale without ongoing cost.
- **Security:** API keys are never hardcoded — always injected via environment variables. CORS is currently open for local development and should be restricted before any production deployment.
- **Possible future improvements:** caching repeated resume/JD comparisons, supporting more file formats, adding a "resume rewrite" feature, rate-limiting per user.

---

## License

This project is for educational and portfolio purposes.
