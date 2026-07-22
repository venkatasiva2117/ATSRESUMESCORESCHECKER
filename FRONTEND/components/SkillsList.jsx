export default function SkillsList({ matchedSkills = [], missingSkills = [] }) {
  return (
    <div className="skills-grid">
      <div className="skills-column matched">
        <h3>Matched Skills</h3>
        <ul>
          {matchedSkills.map((skill) => (
            <li key={skill}>✅ {skill}</li>
          ))}
        </ul>
      </div>
      <div className="skills-column missing">
        <h3>Missing Skills</h3>
        <ul>
          {missingSkills.map((skill) => (
            <li key={skill}>⚠️ {skill}</li>
          ))}
        </ul>
      </div>
    </div>
  );
}
