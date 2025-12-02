import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosClient";

function ReunionPage() {
  const navigate = useNavigate();
  const [reunions, setReunions] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchReunions = async () => {
    try {
      const res = await api.get("/reunions");
      setReunions(res.data);
    } catch (err) {
      console.error(err);
      alert("Erreur : impossible de charger les réunions");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReunions();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Supprimer cette réunion ?")) return;

    try {
      await api.delete(`/reunions/${id}`);
      setReunions(reunions.filter((r) => r.id !== id));
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la suppression");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des réunions</h1>

      <button
        onClick={() => navigate("/reunions/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Nouvelle réunion
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Duree</th>
            <th>Objet</th>
            <th>Type</th>
            <th>État</th>
            <th>Stagiaire</th>
            <th>Projet</th>
            <th>Formateur</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {reunions.map((r) => (
            <tr key={r.id}>
              <td>{r.id}</td>
              <td>{r.date}</td>
              <td>{r.duree} min</td>
              <td>{r.objet}</td>
              <td>{r.type}</td>
              <td>{r.etat}</td>

              <td>
                {r.stagiaire
                  ? `${r.stagiaire.prenom} ${r.stagiaire.nom}`
                  : "—"}
              </td>

              <td>{r.projet ? r.projet.titre : "—"}</td>

              <td>
                {r.formateur
                  ? `${r.formateur.prenom} ${r.formateur.nom}`
                  : "—"}
              </td>

              <td>
                <button onClick={() => navigate(`/reunions/edit/${r.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  onClick={() => handleDelete(r.id)}
                  style={{ color: "red" }}
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

export default ReunionPage;
