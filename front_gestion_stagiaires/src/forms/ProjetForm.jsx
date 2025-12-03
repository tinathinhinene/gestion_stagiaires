// src/forms/ProjetForm.jsx
import { useEffect, useState } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import api from "../api/axiosClient";

function ProjetForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();
  const [searchParams] = useSearchParams();

  const stagiaireIdFromURL = searchParams.get("stagiaire"); // ?stagiaire=1

  const [stages, setStages] = useState([]);
  const [form, setForm] = useState({
    titre: "",
    description: "",
    avancement: 0,
    note: 0,
    stage: { id: "" },
  });

  // Charger les stages + éventuel projet (si edit)
  useEffect(() => {
    async function load() {
      try {
        // 1) Stages
        const resStages = await api.get("/stages");
        const allStages = resStages.data;
        setStages(allStages);

        // Si on vient du profil avec ?stagiaire=ID → sélection auto du stage
        if (mode === "add" && stagiaireIdFromURL) {
          const stageDuStagiaire = allStages.find(
            (s) =>
              s.stagiaire &&
              String(s.stagiaire.id) === String(stagiaireIdFromURL)
          );
          if (stageDuStagiaire) {
            setForm((prev) => ({
              ...prev,
              stage: { id: stageDuStagiaire.id },
            }));
          }
        }

        // 2) Projet si modification
        if (mode === "edit" && id) {
          const resProjet = await api.get(`/projets/${id}`);
          const p = resProjet.data;

          setForm({
            titre: p.titre || "",
            description: p.description || "",
            avancement: p.avancement ?? 0,
            note: p.note ?? 0,
            stage: { id: p.stage?.id || "" },
          });
        }
      } catch (err) {
        console.error("Erreur chargement ProjetForm :", err);
      }
    }

    load();
  }, [id, mode, stagiaireIdFromURL]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleStageChange = (e) => {
    setForm({ ...form, stage: { id: e.target.value } });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/projets/create", form);
      } else {
        await api.put(`/projets/${id}`, form);
      }

      // Après sauvegarde : retour au profil stagiaire si on a l'id
      if (stagiaireIdFromURL) {
        navigate(`/stagiaires/${stagiaireIdFromURL}`);
      } else {
        navigate("/accueil");
      }
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement du projet");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un projet" : "Modifier un projet"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "400px" }}>
        <label>Titre</label>
        <input
          name="titre"
          value={form.titre}
          onChange={handleChange}
          required
        />

        <label>Description</label>
        <textarea
          name="description"
          value={form.description}
          onChange={handleChange}
          required
        />

        <label>Avancement (%)</label>
        <input
          type="number"
          name="avancement"
          value={form.avancement}
          onChange={handleChange}
          min="0"
          max="100"
        />

        <label>Note</label>
        <input
          type="number"
          name="note"
          step="0.1"
          value={form.note}
          onChange={handleChange}
        />

        <label>Stage</label>
        <select
          value={form.stage.id || ""}
          onChange={handleStageChange}
          required
        >
          <option value="">-- Choisir un stage --</option>
          {stages.map((s) => (
            <option key={s.id} value={s.id}>
              {s.stagiaire
                ? `${s.stagiaire.prenom} ${s.stagiaire.nom} (stage ${s.id})`
                : `Stage ${s.id}`}
            </option>
          ))}
        </select>

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>

        <button
          type="button"
          style={{ marginTop: "1rem", marginLeft: "1rem" }}
          onClick={() => navigate(-1)}
        >
          Annuler
        </button>
      </form>
    </div>
  );
}

export default ProjetForm;
