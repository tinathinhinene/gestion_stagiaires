// src/profils/StagiaireProfil.jsx
import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../styles/stagiaireProfil.css";

function StagiaireProfil() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [stagiaire, setStagiaire] = useState(null);
  const [projet, setProjet] = useState(null);
  const [commentaires, setCommentaires] = useState([]);
  const [loading, setLoading] = useState(true);

  // ==============================
  // Chargement stagiaire + projet
  // + commentaires (front only)
  // ==============================
  useEffect(() => {
    const token = localStorage.getItem("token");
    const headers = {
      Authorization: token ? `Bearer ${token}` : "",
    };

    async function loadData() {
      try {
        // 1) Stagiaire
        const stagRes = await fetch(
          `http://localhost:8021/api/stagiaires/${id}`,
          { headers }
        );
        const stagData = await stagRes.json();

        // 2) Tous les projets
        const projRes = await fetch("http://localhost:8021/api/projets", {
          headers,
        });
        const projData = await projRes.json();

        // Projet lié à ce stagiaire (via stage.stagiaire.id)
        const projetDuStagiaire =
          projData.find(
            (p) =>
              p.stage &&
              p.stage.stagiaire &&
              String(p.stage.stagiaire.id) === String(id)
          ) || null;

        setStagiaire(stagData);
        setProjet(projetDuStagiaire);

        // 3) Commentaires (on prend tous, on filtre coté front)
        if (projetDuStagiaire) {
          const comRes = await fetch(
            "http://localhost:8021/api/commentaires",
            { headers }
          );

          if (comRes.ok) {
            const allComments = await comRes.json();
            const commentsForProject = allComments.filter(
              (c) =>
                c.projet &&
                String(c.projet.id) === String(projetDuStagiaire.id)
            );
            setCommentaires(commentsForProject);
          } else {
            setCommentaires([]);
          }
        } else {
          setCommentaires([]);
        }
      } catch (err) {
        console.error("Erreur chargement profil stagiaire :", err);
      } finally {
        setLoading(false);
      }
    }

    loadData();
  }, [id]);

  if (loading) return <div className="profil-container">Chargement...</div>;
  if (!stagiaire)
    return <div className="profil-container">Stagiaire introuvable.</div>;

  // ==============================
  // Centre & Classe
  // centre = stagiaire.classe.centre.nom
  // classe = stagiaire.classe.nom
  // ==============================
  const classe =
    stagiaire.classe?.nom ||
    stagiaire.classe?.nomClasse ||
    "—";

  const centre =
    stagiaire.classe?.centre?.nom ||
    stagiaire.classe?.centre?.nomCentre ||
    stagiaire.centre?.nom ||
    "—";

  // ==============================
  // Avancement pour camembert
  // ==============================
  const avancement = projet?.avancement ?? 0;
  const avancementSafe =
    typeof avancement === "number" && !Number.isNaN(avancement)
      ? Math.min(Math.max(avancement, 0), 100)
      : 0;

  const progressStyle = {
    background: `conic-gradient(#1193c2 0 ${avancementSafe * 3.6}deg, #e5f5fb ${
      avancementSafe * 3.6
    }deg 360deg)`,
  };

  // ==============================
  // Suppression du projet
  // ==============================
  const handleDeleteProjet = async () => {
    if (!projet) return;
    if (!window.confirm("Supprimer ce projet ?")) return;

    try {
      const token = localStorage.getItem("token");
      await fetch(`http://localhost:8021/api/projets/${projet.id}`, {
        method: "DELETE",
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      });
      setProjet(null);
      setCommentaires([]);
    } catch (err) {
      console.error("Erreur suppression projet :", err);
      alert("Impossible de supprimer le projet.");
    }
  };

  // ==============================
  // Suppression commentaire
  // ==============================
  const handleDeleteComment = async (commentId) => {
    if (!window.confirm("Supprimer ce commentaire ?")) return;

    try {
      const token = localStorage.getItem("token");
      await fetch(`http://localhost:8021/api/commentaires/${commentId}`, {
        method: "DELETE",
        headers: {
          Authorization: token ? `Bearer ${token}` : "",
        },
      });

      setCommentaires((prev) => prev.filter((c) => c.id !== commentId));
    } catch (err) {
      console.error("Erreur suppression commentaire :", err);
      alert("Impossible de supprimer le commentaire.");
    }
  };

  return (
    <div className="profil-container">
      {/* ========================== */}
      {/* ENTÊTE STAGIAIRE          */}
      {/* ========================== */}
      <h1 className="profil-title">
        Stagiaire : {stagiaire.prenom} {stagiaire.nom}
      </h1>

      <div className="profil-grid">
        {/* PHOTO */}
        <div className="profil-photo">
          <img
            src={stagiaire.photo || "/images/default-avatar.png"}
            alt="stagiaire"
          />
        </div>

        {/* INFOS */}
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
            <strong>Téléphone :</strong> {stagiaire.tel || "—"}
          </p>
          <p>
            <strong>Date de naissance :</strong> {stagiaire.dateNaiss || "—"}
          </p>
          <p>
            <strong>Adresse :</strong> {stagiaire.adresse || "—"}
          </p>
          <p>
            <strong>Centre :</strong> {centre}
          </p>
          <p>
            <strong>Classe :</strong> {classe}
          </p>
          <p>
            <strong>Statut :</strong> {stagiaire.actif ? "Actif" : "Inactif"}
          </p>
        </div>
      </div>

      {/* ========================== */}
      {/* PROJET DU STAGIAIRE       */}
      {/* ========================== */}
      <section className="projet-section">
        <h2>Projet du stagiaire</h2>

        {!projet ? (
          <>
            <p>Aucun projet associé à ce stagiaire.</p>
            <button
              className="btn-add"
              onClick={() =>
                navigate(`/projets/ajouter?stagiaire=${id}`)
              }
            >
              ➕ Ajouter un projet
            </button>
          </>
        ) : (
          <div className="projet-layout">
            {/* Détail projet */}
            <div className="projet-details">
              <h3 className="projet-title">{projet.titre}</h3>

              <p>
                <strong>Description :</strong>{" "}
                {projet.description || "—"}
              </p>
              <p>
                <strong>Note :</strong>{" "}
                {projet.note != null ? projet.note : "—"}
              </p>
              <p>
                <strong>Avancement :</strong> {avancementSafe}%
              </p>

              <div className="projet-buttons">
                <button
                  className="btn-edit"
                  onClick={() => navigate(`/projets/modifier/${projet.id}?stagiaire=${id}`)}
                >
                  Modifier
                </button>
                <button
                  className="btn-delete"
                  onClick={handleDeleteProjet}
                >
                  Supprimer
                </button>
              </div>
            </div>

            {/* Camembert */}
            <div className="projet-progress-wrapper">
              <div className="projet-progress-circle" style={progressStyle}>
                <div className="projet-progress-inner">
                  <span className="progress-value">{avancementSafe}%</span>
                  <span className="progress-label">avancement</span>
                </div>
              </div>
            </div>
          </div>
        )}
      </section>

      {/* ========================== */}
      {/* COMMENTAIRES              */}
      {/* ========================== */}
      <section className="commentaires-section">
        <h2>Commentaires du projet</h2>

        {!projet && <p>Aucun projet → aucun commentaire.</p>}

        {projet && (
          <button
            className="btn-add"
            onClick={() =>
              navigate(
                `/commentaires/ajouter?projet=${projet.id}&retour=/stagiaires/${id}`
              )
            }
          >
            ➕ Ajouter un commentaire
          </button>
        )}

        {projet && commentaires.length === 0 && (
          <p>Aucun commentaire pour ce projet.</p>
        )}

        {projet && commentaires.length > 0 && (
          <ul className="commentaires-list">
            {commentaires.map((c) => (
              <li key={c.id} className="commentaire-item">
                <div className="comment-header">
                  <strong>
                    {c.formateur
                      ? `${c.formateur.prenom} ${c.formateur.nom}`
                      : "Formateur"}
                  </strong>
                  <span className="comment-date">
                    {c.date
                      ? new Date(c.date).toLocaleString("fr-FR")
                      : ""}
                  </span>
                </div>
                <p className="comment-text">{c.message}</p>

                <div className="comment-buttons">
                  <button
                    className="btn-edit"
                    onClick={() =>
                      navigate(
                        `/commentaires/modifier/${c.id}?retour=/stagiaires/${id}`
                      )
                    }
                  >
                    Modifier
                  </button>
                  <button
                    className="btn-delete"
                    onClick={() => handleDeleteComment(c.id)}
                  >
                    Supprimer
                  </button>
                </div>
              </li>
            ))}
          </ul>
        )}
      </section>

      {/* ========================== */}
      {/* BOUTONS BAS DE PAGE       */}
      {/* ========================== */}
      <div className="profil-buttons">
        <button
          className="edit-button"
          onClick={() => navigate(`/stagiaires/modifier/${id}`)}
        >
          Modifier
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
