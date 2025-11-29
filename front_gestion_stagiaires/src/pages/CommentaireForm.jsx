import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function CommentaireForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [form, setForm] = useState({
    message: "",
    formateur: { id: "" },
    projet: { id: "" }
  });

  const [formateurs, setFormateurs] = useState([]);
  const [projets, setProjets] = useState([]);

  const fetchData = async () => {
    const res1 = await api.get("/formateurs");
    const res2 = await api.get("/projets");

    setFormateurs(res1.data);
    setProjets(res2.data);
  };

  useEffect(() => {
    fetchData();

    if (mode === "edit") {
      api.get(`/commentaires/${id}`).then((res) => {
        const c = res.data;

        setForm({
          message: c.message,
          formateur: { id: c.formateur?.id || "" },
          projet: { id: c.projet?.id || "" }
        });
      });
    }
  }, [id, mode]);

  const handleChange = (e) => {
    setForm({ ...form, message: e.target.value });
  };

  const handleFormateurChange = (e) => {
    setForm({ ...form, formateur: { id: e.target.value } });
  };

  const handleProjetChange = (e) => {
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

      navigate("/commentaires");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement du commentaire");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un commentaire" : "Modifier un commentaire"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>

        <label>Message</label>
        <textarea
          value={form.message}
          onChange={handleChange}
          required
        />

        <label>Formateur</label>
        <select
          value={form.formateur.id}
          onChange={handleFormateurChange}
          required
        >
          <option value="">-- Choisir --</option>
          {formateurs.map((f) => (
            <option key={f.id} value={f.id}>
              {f.prenom} {f.nom}
            </option>
          ))}
        </select>

        <label>Projet</label>
        <select
          value={form.projet.id}
          onChange={handleProjetChange}
          required
        >
          <option value="">-- Choisir --</option>
          {projets.map((p) => (
            <option key={p.id} value={p.id}>
              {p.titre}
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

export default CommentaireForm;
