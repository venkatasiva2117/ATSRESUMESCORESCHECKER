const BASE_URL = "http://localhost:8080";

export async function requestMatch(resumeFile, jobDescription) {
  const formData = new FormData();
  formData.append("resume", resumeFile);
  formData.append("jobDescription", jobDescription);

  const res = await fetch(`${BASE_URL}/match`, {
    method: "POST",
    body: formData,
  });

  if (!res.ok) {
    const errorBody = await res.json().catch(() => null);
    throw new Error(errorBody?.message || "Match equest failed");
  }
  return res.json();
}

export default requestMatch;
