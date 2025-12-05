// src/forms/ReunionForm.jsx
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/reunionForm.css";

function ReunionForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [stagiaires, setStagiaires] = useState([]);
  const [projets, setProjets] = useState([]);

  const [form, setForm] = useState({
    date: "",
    duree: "",
    objet: "",
    description: "",
    type: "initiale",
    actions: "",
    etat: "plannifiee",
    stagiaire: { id: "" },
    projet: { id: "" },
  });

  const fetchData = async () => {
    try {
      const [s, p] = await Promise.all([
        api.get("/stagiaires"),
        api.get("/projets"),
      ]);

      setStagiaires(s.data);
      setProjets(p.data);

      if (mode === "edit") {
        const r = await api.get(`/reunions/${id}`);
        setForm({
          date: r.data.date,
          duree: r.data.duree,
          objet: r.data.objet,
          description: r.data.description,
          type: r.data.type,
          actions: r.data.actions,
          etat: r.data.etat,
          stagiaire: { id: r.data.stagiaire?.id || "" },
          projet: { id: r.data.projet?.id || "" },
        });
      }
    } catch (err) {
      console.error(err);
      alert("Erreur lors du chargement des données");
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  // Formateur automatique = user connecté
  const getConnectedFormateur = () => {
    const tokenData = JSON.parse(localStorage.getItem("user"));
    return { id: tokenData?.formateur?.id || null };
  };

  const onChange = (e) =>
    setForm({ ...form, [e.target.name]: e.target.value });

  const onSelect = (e, key) =>
    setForm({ ...form, [key]: { id: e.target.value } });

  const submit = async (e) => {
    e.preventDefault();

    const payload = {
      ...form,
      formateur: getConnectedFormateur(),
    };

    try {
      if (mode === "add") {
        await api.post("/reunions/create", payload);
      } else {
        await api.put(`/reunions/${id}`, payload);
      }
      navigate("/reunions");
    } catch (err) {
      console.log(err);
      alert("Erreur lors de l’enregistrement");
    }
  };

  return (
    <div className="reunion-form-container">
      <h1 className="title">
        {mode === "add" ? "Nouvelle réunion" : "Modifier la réunion"}
      </h1>

      <form className="form-card" onSubmit={submit}>
        <div className="row">
          <div className="field">
            <label>Date</label>
            <input
              type="datetime-local"
              name="date"
              value={form.date}
              onChange={onChange}
              required
            />
          </div>

          <div className="field">
            <label>Durée (minutes)</label>
            <input
              type="number"
              name="duree"
              value={form.duree}
              onChange={onChange}
            />
          </div>
        </div>

        <div className="field">
          <label>Objet</label>
          <input
            name="objet"
            value={form.objet}
            onChange={onChange}
            required
          />
        </div>

        <div className="field">
          <label>Description</label>
          <textarea
            name="description"
            value={form.description}
            onChange={onChange}
            required
          />
        </div>

        <div className="field">
          <label>Actions prévues</label>
          <textarea
            name="actions"
            value={form.actions}
            onChange={onChange}
          />
        </div>

        <div className="row">
          <div className="field">
            <label>Type</label>
            <select name="type" value={form.type} onChange={onChange}>
              <option value="initiale">initiale</option>
              <option value="suivi">suivi</option>
              <option value="fin_stage">fin_stage</option>
            </select>
          </div>

          <div className="field">
            <label>État</label>
            <select name="etat" value={form.etat} onChange={onChange}>
              <option value="plannifiee">plannifiee</option>
              <option value="realisee">realisee</option>
              <option value="annulee">annulee</option>
            </select>
          </div>
        </div>

        <div className="field">
          <label>Stagiaire</label>
          <select
            value={form.stagiaire.id}
            onChange={(e) => onSelect(e, "stagiaire")}
            required
          >
            <option value="">-- Choisir un stagiaire --</option>

            {stagiaires.map((s) => (
              <option value={s.id} key={s.id}>
                {s.prenom} {s.nom}
              </option>
            ))}
          </select>
        </div>

        <div className="field">
          <label>Projet (facultatif)</label>
          <select
            value={form.projet.id}
            onChange={(e) => onSelect(e, "projet")}
          >
            <option value="">-- Aucun projet --</option>

            {projets.map((p) => (
              <option value={p.id} key={p.id}>
                {p.titre}
              </option>
            ))}
          </select>
        </div>

        <button className="btn-submit">Enregistrer</button>
      </form>
    </div>
  );
}

export default ReunionForm;
