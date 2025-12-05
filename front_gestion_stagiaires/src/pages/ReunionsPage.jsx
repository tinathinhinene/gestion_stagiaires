// src/pages/ReunionPage.jsx
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/reunions.css";

function ReunionsPage() {
  const navigate = useNavigate();
  const [reunions, setReunions] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchReunions = async () => {
    try {
      const res = await api.get("/reunions");
      setReunions(res.data);
    } catch (err) {
      alert("Erreur : impossible de charger les réunions");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchReunions();
  }, []);

  const handleDelete = async (id) => {
    if (!window.confirm("Supprimer cette réunion ?")) return;

    try {
      await api.delete(`/reunions/${id}`);
      setReunions((prev) => prev.filter((r) => r.id !== id));
    } catch (err) {
      alert("Impossible de supprimer la réunion.");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div className="reunion-container">
      <h1 className="reunion-title">Liste des réunions</h1>

      <button
        className="reunion-add-btn"
        onClick={() => navigate("/reunions/new")}
      >
        ➕ Nouvelle réunion
      </button>
<table className="reunion-table">
  <thead>
    <tr>
      <th>ID</th>
      <th>Date</th>
      <th>Durée</th>
      <th>Objet</th>
      <th>Description</th>
      <th>Actions prévues</th>
      <th>Type</th>
      <th>État</th>
      <th>Stagiaire</th>
      <th>Projet</th>
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

        {/* 🟦 Description */}
        <td style={{ maxWidth: "250px", whiteSpace: "normal" }}>
          {r.description}
        </td>

        {/* 🟦 Actions prévues */}
        <td style={{ maxWidth: "250px", whiteSpace: "normal" }}>
          {r.actions || "—"}
        </td>

        <td>{r.type}</td>
        <td>{r.etat}</td>

        {/* 🟦 Stagiaire */}
        <td>
          {r.stagiaire
            ? `${r.stagiaire.prenom} ${r.stagiaire.nom}`
            : "—"}
        </td>

        {/* 🟦 Projet */}
        <td>{r.projet ? r.projet.titre : "—"}</td>

        <td>
          <button
            className="action-btn action-edit"
            onClick={() => navigate(`/reunions/edit/${r.id}`)}
          >
            Modifier
          </button>

          &nbsp;

          <button
            className="action-btn action-delete"
            onClick={() => handleDelete(r.id)}
          >
            Supprimer
          </button>
        </td>
      </tr>
    ))}
  </tbody>
</table>
s
      
    </div>
  );
}

export default ReunionsPage;
