import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function DocumentForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [stages, setStages] = useState([]);
  const [file, setFile] = useState(null);

  const [form, setForm] = useState({
    nom: "",
    type: "",
    stageId: ""
  });

  // Charger la liste des stages
  const fetchStages = async () => {
    const res = await api.get("/stages");
    setStages(res.data);
  };

  useEffect(() => {
    fetchStages();

    if (mode === "edit") {
      api.get(`/documents/${id}`).then((res) => {
        const d = res.data;
        setForm({
          nom: d.nom,
          type: d.type,
          stageId: d.stage?.id || ""
        });
      });
    }
  }, [mode, id]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleFile = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        const data = new FormData();
        data.append("file", file);
        data.append("nom", form.nom);
        data.append("type", form.type);
        data.append("stageId", form.stageId);

        await api.post("/documents/create", data, {
          headers: { "Content-Type": "multipart/form-data" }
        });
      } else {
        await api.put(`/documents/${id}`, {
          nom: form.nom,
          type: form.type,
          stage: { id: form.stageId }
        });
      }

      navigate("/documents");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement du document");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un document" : "Modifier un document"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        <label>Nom</label>
        <input
          name="nom"
          value={form.nom}
          onChange={handleChange}
          required
        />

        <label>Type</label>
        <input
          name="type"
          value={form.type}
          onChange={handleChange}
          required
        />

        <label>Stage</label>
        <select
          name="stageId"
          value={form.stageId}
          onChange={handleChange}
          required
        >
          <option value="">-- Choisir un stage --</option>
          {stages.map((s) => (
            <option key={s.id} value={s.id}>
              {s.stagiaire
                ? `${s.stagiaire.prenom} ${s.stagiaire.nom}`
                : `Stage ${s.id}`}
            </option>
          ))}
        </select>

        {mode === "add" && (
          <>
            <label>Fichier</label>
            <input type="file" onChange={handleFile} required />
          </>
        )}

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default DocumentForm;
