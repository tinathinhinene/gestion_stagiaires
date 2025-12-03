import TimelineBar from "./TimelineBar";

function Timeline({ year, setYear, stages }) {
  return (
    <div className="timeline-wrapper">

      <div className="timeline-year-controls">
        <button onClick={() => setYear(year - 1)}>←</button>
        <span>{year}</span>
        <button onClick={() => setYear(year + 1)}>→</button>
      </div>

      <div className="timeline-months">
        {[
          "janvier","février","mars","avril","mai","juin",
          "juillet","août","septembre","octobre","novembre","décembre"
        ].map((m, i) => (
          <div key={i} className="timeline-month">{m}</div>
        ))}
      </div>

      <div className="timeline-list">
        {stages.length === 0 && (
          <div className="no-stage">Aucun stage pour cette année</div>
        )}

        {stages.map((s) => (
          <TimelineBar
            key={s.id}
            nom={`${s.stagiaire.prenom} ${s.stagiaire.nom}`}
            debut={s.dateDebut}
            fin={s.dateFin}
            year={year}
          />
        ))}
      </div>
    </div>
  );
}

export default Timeline;
