function TimelineBar({ nom, debut, fin }) {
  const d1 = new Date(debut);
  const d2 = new Date(fin);
  const today = new Date();

  const total = d2 - d1;
  const progress = Math.min(100, Math.max(0, ((today - d1) / total) * 100));

  return (
    <div className="timeline-row">
      <span className="timeline-name">{nom}</span>
      <div className="timeline-bar">
        <div className="timeline-fill" style={{ width: `${progress}%` }} />
      </div>
    </div>
  );
}

export default TimelineBar;
