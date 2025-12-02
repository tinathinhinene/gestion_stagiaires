import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosClient";

function StagePage() {
  const navigate = useNavigate();
  const [stages, setStages] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchStages = async () => {
    try {
      const res = await api.get("/stages");
      setStages(res.data);
    } catch (err) {
      console.error(err);
      alert("Impossible de charger les stages");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStages();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Voulez-vous supprimer ce stage ?")) return;

    try {
      await api.delete(`/stages/${id}`);
      setStages(stages.filter((s) => s.id !== id));
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la suppression");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des stages</h1>

      <button
        onClick={() => navigate("/stages/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Ajouter un stage
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Date début</th>
            <th>Date fin</th>
            <th>État</th>
            <th>Convention Signée</th>
            <th>Attestation Livrée</th>
            <th>Stagiaire</th>
            <th>Projet</th>
            </tr>
        </thead>

        <tbody>
          {stages.map((s) => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.dateDebut}</td>
              <td>{s.dateFin}</td>
              <td>{s.etat}</td>
              <td>{s.conventionSignee ? "Oui" : "Non"}</td>
              <td>{s.attestationLivree ? "Oui" : "Non"}</td>

              <td>
                {s.stagiaire
                  ? `${s.stagiaire.prenom} ${s.stagiaire.nom}`
                  : "—"}
              </td>

              <td>{s.projet ? s.projet.titre : "—"}</td>

              <td>
                <button onClick={() => navigate(`/stages/edit/${s.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  style={{ color: "red" }}
                  onClick={() => handleDelete(s.id)}
                >
                  Supprimer
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default StagePage;
