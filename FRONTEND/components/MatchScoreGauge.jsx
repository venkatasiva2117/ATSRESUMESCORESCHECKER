import { RadialBarChart, RadialBar, PolarAngleAxis } from "recharts";

export default function MatchScoreGauge({ percent }) {
  const data = [{ name: "match", value: percent, fill: getColor(percent) }];

  function getColor(p) {
    if (p >= 75) return "#22c55e";
    if (p >= 50) return "#eab308";
    return "#ef4444";
  }

  return (
    <div className="score-gauge">
      <RadialBarChart
        width={220}
        height={220}
        cx="50%"
        cy="50%"
        innerRadius="70%"
        outerRadius="100%"
        barSize={18}
        data={data}
        startAngle={90}
        endAngle={-270}
      >
        <PolarAngleAxis
          type="number"
          domain={[0, 100]}
          angleAxisId={0}
          tick={false}
        />
        <RadialBar background dataKey="value" cornerRadius={10} />
      </RadialBarChart>
      <div className="score-label">{percent}%</div>
      console.log(result.matchPercent);
      <p className="score-caption">Match Score</p>
    </div>
  );
}
