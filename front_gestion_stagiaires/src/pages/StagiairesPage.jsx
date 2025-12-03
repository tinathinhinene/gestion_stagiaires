// src/pages/StagiairesPage.jsx
import { useEffect, useState } from "react";
import api from "../api/axiosClient";
import { useNavigate } from "react-router-dom";
import "../styles/stagiaires.css";
import StagiaireCarousel from "../components/StagiaireCarousel";

function StagiairesPage() {
  const [stagiaires, setStagiaires] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    loadStagiaires();
  }, []);

  const loadStagiaires = async () => {
    try {
      const res = await api.get("/stagiaires");
      setStagiaires(res.data);
    } catch (err) {
      console.error("Erreur chargement stagiaires :", err);
    }
  };

  const supprimer = async (id) => {
    if (!window.confirm("Supprimer ce stagiaire ?")) return;

    try {
      await api.delete(`/stagiaires/${id}`);
      setStagiaires((prev) => prev.filter((s) => s.id !== id));
    } catch (err) {
      alert("Suppression impossible");
    }
  };

  return (
    <div className="page-container">
      <h1>Stagiaires</h1>

      <div className="add-container">
        <button
          className="add-btn"
          onClick={() => navigate("/stagiaires/ajouter")}
        >
          ➕ Ajouter un stagiaire
        </button>
      </div>

      <table className="stagiaire-table">
        <thead>
          <tr>
            <th>Nom du stagiaire</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Date de naissance</th>
            <th>État</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {stagiaires.map((s) => (
            <tr key={s.id}>
              <td>{s.prenom} {s.nom}</td>
              <td>{s.email}</td>
              <td>{s.tel || "—"}</td>
              <td>{s.dateNaiss || "—"}</td>
              <td>{s.actif ? "actif" : "inactif"}</td>

              <td>
                <button
                  className="edit-btn"
                  onClick={() => navigate(`/stagiaires/modifier/${s.id}`)}
                >
                  Modifier
                </button>

                <button
                  className="delete-btn"
                  onClick={() => supprimer(s.id)}
                >
                  Supprimer
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {stagiaires.length > 0 && (
        <StagiaireCarousel stagiaires={stagiaires} />
      )}
    </div>
  );
}

export default StagiairesPage;
