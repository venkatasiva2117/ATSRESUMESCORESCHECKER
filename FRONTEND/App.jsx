import { useState } from "react";
import UploadForm from "./components/UploadForm";
import MatchScoreGauge from "./components/MatchScoreGauge";
import SkillsList from "./components/SkillsList";
import SuggestionsList from "./components/SuggestionsList";
import { requestMatch } from "./services/api";
import "./App.css";

function App() {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = async (resumeFile, jobDescription) => {
    setLoading(true);
    setError("");
    setResult(null);
    try {
      const data = await requestMatch(resumeFile, jobDescription);
      setResult(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      <h1>ATS RESUME SCORE CHECKER</h1>
      <p className="subtitle">
        Upload your resume and a job description to see how well you match.
      </p>

      <UploadForm onSubmit={handleSubmit} loading={loading} />

      {error && <p className="app-error">{error}</p>}
      {result && (
        <div className="results">
          <MatchScoreGauge percent={result.matchPercentage} />

          <SkillsList
            matchedSkills={result.matchedSkills}
            missingSkills={result.missingSkills}
          />
          <SuggestionsList suggestions={result.suggestions} />
        </div>
      )}
    </div>
  );
}

export default App;
