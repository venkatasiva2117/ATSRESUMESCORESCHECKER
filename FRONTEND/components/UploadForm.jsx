import { useState } from "react";
import "../App.css";

export default function UploadForm({ onSubmit, loading }) {
  const [resumeFile, setResumeFile] = useState(null);
  const [jobDescription, setJobDescription] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!resumeFile) {
      setError("Please upload a resume file (PDF or DOCX).");
      return;
    }
    if (!jobDescription.trim()) {
      setError("Please paste a job description.");
      return;
    }
    setError("");
    onSubmit(resumeFile, jobDescription);
  };

  return (
    <form onSubmit={handleSubmit} className="upload-form">
      <div className="form-group">
        <label htmlFor="resume">Upload Resume (PDF, DOCX, ect... )</label>
        <input
          id="resume"
          type="file"
          accept=".pdf,.docx"
          onChange={(e) => setResumeFile(e.target.files[0])}
        />
      </div>
      <div className="form-group">
        <label htmlFor="jd">Job Description</label>
        <textarea
          id="jd"
          rows={10}
          placeholder="Paste the job description here..."
          value={jobDescription}
          onChange={(e) => setJobDescription(e.target.value)}
        />
      </div>

      {error && <p className="form-error">{error}</p>}

      <button type="submit" disabled={loading}>
        {loading ? "Analyzing..." : "Analyze Match"}
      </button>
    </form>
  );
}
