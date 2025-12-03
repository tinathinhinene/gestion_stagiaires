function TimelineBar({ nom, debut, fin, year }) {
  const start = new Date(debut);
  const end = new Date(fin);

  const yearStart = new Date(year, 0, 1);
  const yearEnd = new Date(year, 11, 31, 23, 59, 59);

  // Tronquer aux bornes de l'année
  const visibleStart = start < yearStart ? yearStart : start;
  const visibleEnd = end > yearEnd ? yearEnd : end;

  // Position dans l'année en pourcentage
  const startPos = (visibleStart.getMonth() * 30.44 + visibleStart.getDate()) / 365;
  const endPos = (visibleEnd.getMonth() * 30.44 + visibleEnd.getDate()) / 365;

  const left = (startPos * 100).toFixed(2) + "%";
  const width = ((endPos - startPos) * 100).toFixed(2) + "%";

  return (
    <div className="timeline-row">
      <div className="timeline-name">{nom}</div>

      <div className="timeline-bar">
        <div className="timeline-fill" style={{ left, width }}>
          <span className="timeline-dates">
            {visibleStart.toLocaleDateString("fr-FR")} — {visibleEnd.toLocaleDateString("fr-FR")}
          </span>
        </div>
      </div>
    </div>
  );
}

export default TimelineBar;
