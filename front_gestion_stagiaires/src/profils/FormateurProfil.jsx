// src/profil/FormateurProfil.jsx
import { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/formateurProfil.css";

function FormateurProfil() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [formateur, setFormateur] = useState(null);
  const [stagiaires, setStagiaires] = useState([]);

  useEffect(() => {
    loadFormateur();
  }, [id]);

  const loadFormateur = async () => {
    try {
      // Récupère les infos du formateur
      const resFormateur = await api.get(`/formateurs/${id}`);
      setFormateur(resFormateur.data);

      // Récupère ses stagiaires
      const resStagiaires = await api.get(`/formateurs/${id}/stagiaires`);
      setStagiaires(resStagiaires.data);

    } catch (err) {
      console.error("Erreur chargement formateur :", err);
    }
  };

  if (!formateur) {
    return <p style={{ padding: "20px" }}>Chargement...</p>;
  }

  const getPhoto = (prenom, nom) => {
    try {
      return new URL(
        `../assets/images/${prenom}_${nom}.png`,
        import.meta.url
      ).href;
    } catch {
      return new URL(`../assets/images/default.png`, import.meta.url).href;
    }
  };

  return (
    <div className="formateur-profil-container">

      {/* HEADER */}
      <div className="fp-header">
        <img
          className="fp-photo"
          src={getPhoto(formateur.prenom, formateur.nom)}
          alt={formateur.prenom}
        />

        <h1>{formateur.prenom} {formateur.nom}</h1>
        <p className="fp-email">{formateur.email}</p>
      </div>

      {/* INFOS */}
      <div className="fp-info">
        <p><strong>Email :</strong> {formateur.email}</p>
        <p><strong>Téléphone :</strong> {formateur.tel || "—"}</p>
      </div>

      {/* STAGIAIRES */}
      <h2>Ses stagiaires</h2>

      <div className="fp-stagiaires-list">
        {stagiaires.length === 0 && <p>Aucun stagiaire associé.</p>}

        {stagiaires.map((s) => (
          <div key={s.id} className="fp-stagiaire-card">
            <img
              src={getPhoto(s.prenom, s.nom)}
              alt={s.prenom}
              className="fp-stagiaire-photo"
            />

            <Link to={`/stagiaires/${s.id}`} className="fp-stagiaire-name">
              {s.prenom} {s.nom}
            </Link>
          </div>
        ))}
      </div>

      {/* BOUTONS */}
      <div className="fp-buttons">
        <button
          className="fp-edit"
          onClick={() => navigate(`/formateurs/modifier/${id}`)}
        >
          Modifier
        </button>

        <button
          className="fp-back"
          onClick={() => navigate(-1)}
        >
          Retour
        </button>
      </div>

    </div>
  );
}

export default FormateurProfil;
