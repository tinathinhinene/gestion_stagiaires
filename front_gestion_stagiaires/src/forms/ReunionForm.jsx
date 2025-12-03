import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";

function ReunionForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [formateurs, setFormateurs] = useState([]);
  const [stagiaires, setStagiaires] = useState([]);
  const [projets, setProjets] = useState([]);

  const [form, setForm] = useState({
    date: "",
    duree: "",
    objet: "",
    description: "",
    type: "BILAN",
    actions: "",
    etat: "PLANIFIEE",
    formateur: { id: "" },
    stagiaire: { id: "" },
    projet: { id: "" }
  });

  const fetchLists = async () => {
    const f = await api.get("/formateurs");
    const s = await api.get("/stagiaires");
    const p = await api.get("/projets");

    setFormateurs(f.data);
    setStagiaires(s.data);
    setProjets(p.data);
  };

  useEffect(() => {
    fetchLists();

    if (mode === "edit") {
      api.get(`/reunions/${id}`).then((res) => {
        const r = res.data;

        setForm({
          date: r.date,
          duree: r.duree,
          objet: r.objet,
          description: r.description,
          type: r.type,
          actions: r.actions,
          etat: r.etat,
          formateur: { id: r.formateur?.id || "" },
          stagiaire: { id: r.stagiaire?.id || "" },
          projet: { id: r.projet?.id || "" }
        });
      });
    }
  }, [id, mode]);

  const onChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const onSelect = (e, field) =>
    setForm({ ...form, [field]: { id: e.target.value } });

  const submitForm = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/reunions/create", form);
      } else {
        await api.put(`/reunions/${id}`, form);
      }

      navigate("/reunions");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Nouvelle réunion" : "Modifier la réunion"}</h1>

      <form onSubmit={submitForm} style={{ maxWidth: "350px" }}>
        <label>Date</label>
        <input
          type="datetime-local"
          name="date"
          value={form.date}
          onChange={onChange}
          required
        />

        <label>Durée (minutes)</label>
        <input
          type="number"
          name="duree"
          value={form.duree}
          onChange={onChange}
          required
        />

        <label>Objet</label>
        <input
          name="objet"
          value={form.objet}
          onChange={onChange}
          required
        />

        <label>Description</label>
        <textarea
          name="description"
          value={form.description}
          onChange={onChange}
          required
        />

        <label>Actions prévues</label>
        <textarea
          name="actions"
          value={form.actions}
          onChange={onChange}
        />

        <label>Type</label>
        <select name="type" value={form.type} onChange={onChange}>
          <option value="BILAN">BILAN</option>
          <option value="TECHNIQUE">TECHNIQUE</option>
          <option value="ENTRETIEN">ENTRETIEN</option>
        </select>

        <label>État</label>
        <select name="etat" value={form.etat} onChange={onChange}>
          <option value="PLANIFIEE">PLANIFIEE</option>
          <option value="TERMINEE">TERMINEE</option>
          <option value="ANNULEE">ANNULEE</option>
        </select>

        <label>Formateur</label>
        <select
          value={form.formateur.id}
          onChange={(e) => onSelect(e, "formateur")}
          required
        >
          <option value="">-- Choisir --</option>
          {formateurs.map((f) => (
            <option key={f.id} value={f.id}>
              {f.prenom} {f.nom}
            </option>
          ))}
        </select>

        <label>Stagiaire</label>
        <select
          value={form.stagiaire.id}
          onChange={(e) => onSelect(e, "stagiaire")}
          required
        >
          <option value="">-- Choisir --</option>
          {stagiaires.map((s) => (
            <option key={s.id} value={s.id}>
              {s.prenom} {s.nom}
            </option>
          ))}
        </select>

        <label>Projet</label>
        <select
          value={form.projet.id}
          onChange={(e) => onSelect(e, "projet")}
        >
          <option value="">-- Choisir (optionnel) --</option>
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

export default ReunionForm;
