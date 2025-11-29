function StatCard({ title, value }) {
  return (
    <div className="stat-card">
      <h3>{title}</h3>
      <span className="stat-value">{value}</span>
    </div>
  );
}

export default StatCard;
