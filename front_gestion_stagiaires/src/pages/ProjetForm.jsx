import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";

function ProjetForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [stages, setStages] = useState([]);

  const [form, setForm] = useState({
    titre: "",
    description: "",
    avancement: 0,
    note: 0,
    stage: { id: null }
  });

  const fetchStages = async () => {
    const res = await api.get("/stages");
    setStages(res.data);
  };

  useEffect(() => {
    fetchStages();

    if (mode === "edit") {
      api.get(`/projets/${id}`).then((res) => {
        const p = res.data;

        setForm({
          titre: p.titre,
          description: p.description,
          avancement: p.avancement,
          note: p.note,
          stage: { id: p.stage?.id || "" }
        });
      });
    }
  }, [id, mode]);

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

      navigate("/projets");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement du projet");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un projet" : "Modifier un projet"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        <label>Titre</label>
        <input name="titre" value={form.titre} onChange={handleChange} required />

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
        <select value={form.stage.id || ""} onChange={handleStageChange}>
          <option value="">-- Choisir un stage --</option>
          {stages.map((s) => (
            <option key={s.id} value={s.id}>
              {s.stagiaire ? `${s.stagiaire.prenom} ${s.stagiaire.nom}` : "Stage"}
            </option>
          ))}
        </select>

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default ProjetForm;
