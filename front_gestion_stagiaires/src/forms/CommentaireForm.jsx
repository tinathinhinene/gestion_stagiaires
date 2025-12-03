// src/forms/CommentaireForm.jsx
import { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import api from "../api/axiosClient";

function CommentaireForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();
  const [searchParams] = useSearchParams();

  const projetIdFromURL = searchParams.get("projet");
  const retour = searchParams.get("retour"); // ex: /stagiaires/1

  const [projets, setProjets] = useState([]);
  const [form, setForm] = useState({
    message: "",
    projet: { id: "" },
  });

  // Charger projets + commentaire si edit
  useEffect(() => {
    async function load() {
      try {
        const resProj = await api.get("/projets");
        const allProjets = resProj.data;
        setProjets(allProjets);

        if (mode === "add" && projetIdFromURL) {
          setForm((prev) => ({
            ...prev,
            projet: { id: projetIdFromURL },
          }));
        }

        if (mode === "edit" && id) {
          const resCom = await api.get(`/commentaires/${id}`);
          const c = resCom.data;

          setForm({
            message: c.message || "",
            projet: { id: c.projet?.id || "" },
          });
        }
      } catch (err) {
        console.error("Erreur chargement CommentaireForm :", err);
      }
    }

    load();
  }, [id, mode, projetIdFromURL]);

  const handleChangeMessage = (e) => {
    setForm({ ...form, message: e.target.value });
  };

  const handleChangeProjet = (e) => {
    setForm({ ...form, projet: { id: e.target.value } });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/commentaires/create", form);
      } else {
        await api.put(`/commentaires/${id}`, form);
      }

      if (retour) {
        navigate(retour);
      } else {
        navigate(-1);
      }
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement du commentaire");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>
        {mode === "add"
          ? "Ajouter un commentaire"
          : "Modifier un commentaire"}
      </h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "400px" }}>
        <label>Message</label>
        <textarea
          value={form.message}
          onChange={handleChangeMessage}
          required
        />

        <label>Projet</label>
        <select
          value={form.projet.id || ""}
          onChange={handleChangeProjet}
          required
        >
          <option value="">-- Choisir un projet --</option>
          {projets.map((p) => (
            <option key={p.id} value={p.id}>
              {p.titre}
            </option>
          ))}
        </select>

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>

        <button
          type="button"
          style={{ marginTop: "1rem", marginLeft: "1rem" }}
          onClick={() => (retour ? navigate(retour) : navigate(-1))}
        >
          Annuler
        </button>
      </form>
    </div>
  );
}

export default CommentaireForm;
