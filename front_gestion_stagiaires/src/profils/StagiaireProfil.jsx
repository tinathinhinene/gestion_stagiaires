import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../styles/stagiaireProfil.css";

function StagiaireProfil() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [stagiaire, setStagiaire] = useState(null);
  const [projet, setProjet] = useState(null);

  // Charger stagiaire + projet lié
  useEffect(() => {
    const token = localStorage.getItem("token");

    // 1) Infos stagiaire
    fetch(`http://localhost:8021/api/stagiaires/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((r) => r.json())
      .then((d) => {
        console.log("Stagiaire :", d);
        setStagiaire(d);
      })
      .catch(console.error);

    // 2) Tous les projets → on filtre celui de ce stagiaire
    fetch("http://localhost:8021/api/projets", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((r) => r.json())
      .then((data) => {
        console.log("Projets :", data);

        // On cherche le projet dont le stage appartient à ce stagiaire
        const p = data.find(
          (prj) =>
            prj.stage &&
            prj.stage.stagiaire &&
            String(prj.stage.stagiaire.id) === String(id)
        );

        setProjet(p || null);
      })
      .catch(console.error);
  }, [id]);

  // Suppression projet
  const deleteProjet = async (projetId) => {
    const token = localStorage.getItem("token");
    if (!window.confirm("Supprimer ce projet ?")) return;

    try {
      await fetch(`http://localhost:8021/api/projets/${projetId}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });
      setProjet(null);
    } catch (e) {
      console.error(e);
      alert("Erreur lors de la suppression");
    }
  };

  if (!stagiaire) return <div className="profil-container">Chargement...</div>;

  return (
    <div className="profil-container">
      {/* TITRE */}
      <h1>
        Stagiaire : {stagiaire.prenom} {stagiaire.nom}
      </h1>

      {/* ====== INFOS STAGIAIRE ====== */}
      <div className="profil-grid">
        {/* PHOTO */}
        <div className="profil-photo">
          <img
            src={stagiaire.photo || "/images/default-avatar.png"}
            alt="photo stagiaire"
          />
        </div>

        {/* INFOS TEXTE */}
        <div className="profil-info">
          <p>
            <strong>Prénom :</strong> {stagiaire.prenom}
          </p>
          <p>
            <strong>Nom :</strong> {stagiaire.nom}
          </p>
          <p>
            <strong>Email :</strong> {stagiaire.email}
          </p>
          <p>
            <strong>Téléphone :</strong> {stagiaire.tel}
          </p>
          <p>
            <strong>Date de naissance :</strong> {stagiaire.dateNaiss}
          </p>

          <p>
            <strong>Adresse :</strong> {stagiaire.adresse || "—"}
          </p>
          <p>
            <strong>Centre :</strong> {stagiaire.centre?.nomCentre || "—"}
          </p>
          <p>
            <strong>Classe :</strong> {stagiaire.classe?.nomClasse || "—"}
          </p>
          <p>
            <strong>Statut :</strong> {stagiaire.actif ? "Actif" : "Inactif"}
          </p>
        </div>
      </div>

      {/* ====== PROJET DU STAGIAIRE ====== */}
      <h2>Projet du stagiaire</h2>

      {!projet ? (
        <div>
          <p>Aucun projet pour ce stagiaire.</p>
          <button
            className="btn-add"
            onClick={() => navigate(`/projets/ajouter?stagiaire=${id}`)}
          >
            ➕ Ajouter un projet
          </button>
        </div>
      ) : (
        <div className="projet-card">
          <h3>{projet.titre}</h3>
          <p>
            <strong>Description :</strong> {projet.description}
          </p>
          <p>
            <strong>Avancement :</strong> {projet.avancement}%{" "}
          </p>
          <p>
            <strong>Note :</strong> {projet.note}
          </p>

          <div className="projet-buttons">
            <button
              className="btn-edit"
              onClick={() => navigate(`/projets/modifier/${projet.id}`)}
            >
              Modifier
            </button>

            <button
              className="btn-delete"
              onClick={() => deleteProjet(projet.id)}
            >
              Supprimer
            </button>
          </div>
        </div>
      )}

      {/* ====== BOUTONS BAS DE PAGE ====== */}
      <div className="profil-buttons">
        <button
          className="edit-button"
          onClick={() => navigate(`/stagiaires/modifier/${id}`)}
        >
          Modifier le stagiaire
        </button>

        <button
          className="back-button"
          onClick={() => navigate("/stagiaires")}
        >
          Retour
        </button>
      </div>
    </div>
  );
}

export default StagiaireProfil;
