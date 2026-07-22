export default function SuggestionsList({ suggestions = [] }) {
  if (!suggestions.length) return null;

  return (
    <div className="suggestions">
      <h3>Suggestions to Improve Your Match</h3>
      <ul>
        {suggestions.map((tip, idx) => (
          <li key={idx}>{tip}</li>
        ))}
      </ul>
    </div>
  );
}
