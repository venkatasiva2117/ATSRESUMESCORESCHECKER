# AI Resume / Job Matcher — Frontend

React + Vite web app that lets users upload a resume and paste a job description to see how well they match — powered by an AI-driven Spring Boot backend using Google's Gemini API.

## Tech Stack

- **React 18**
- **Vite** — build tool and dev server
- **Recharts** — radial score gauge visualization
- **Fetch API** — communication with the backend

## Features

- Upload a resume (PDF or DOCX)
- Paste any job description
- View results instantly:
  - Visual match score gauge (color-coded: red/yellow/green)
  - Side-by-side matched vs. missing skills
  - AI-generated suggestions to improve the resume
- Clean, responsive dark-mode UI

## Screenshots

*(Add a screenshot of the UI here once deployed, e.g. `![App Screenshot](./screenshot.png)`)*

## Getting Started

### Prerequisites

- Node.js 18+
- The backend service running (see [backend README](../backend/README.md)) — defaults to `http://localhost:8080`

### Setup

1. Clone the repository
   ```bash
   git clone <your-repo-url>
   cd frontend
   ```

2. Install dependencies
   ```bash
   npm install
   ```

3. Run the development server
   ```bash
   npm run dev
   ```

4. Open the app at `http://localhost:5173`

### Configuration

The backend base URL is currently set in `src/services/api.js`:
```javascript
const BASE_URL = "http://localhost:8080";
```
Update this if your backend is deployed elsewhere (e.g. Render).

## Project Structure

```
src/
├── components/
│   ├── UploadForm.jsx        # Resume upload + job description input
│   ├── MatchScoreGauge.jsx   # Radial score visualization
│   ├── SkillsList.jsx        # Matched vs. missing skills
│   └── SuggestionsList.jsx   # AI-generated improvement suggestions
├── services/
│   └── api.js                 # API calls to the Spring Boot backend
├── App.jsx
├── App.css
└── main.jsx
```

## How It Works

1. User selects a resume file and pastes a job description
2. On submit, the form sends both to the backend as `multipart/form-data`
3. The backend returns a match score, skills breakdown, and suggestions
4. The UI renders the score as a radial gauge, skills as a two-column comparison, and suggestions as a list

## Build for Production

```bash
npm run build
```
Output is generated in the `dist/` folder, ready to deploy to Netlify, Vercel, or any static host.

## Notes

- Requires the backend to be running and reachable at the configured `BASE_URL`
- CORS must be enabled on the backend for the frontend's origin

## License

This project is for educational/portfolio purposes.
