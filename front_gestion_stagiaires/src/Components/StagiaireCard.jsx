function StagiaireCard({ nom, prenom, photo }) {
  return (
    <div className="stagiaire-card">
      <div className="avatar"></div>

      <strong>{prenom}</strong>
      <span>{nom}</span>
    </div>
  );
}

export default StagiaireCard;
