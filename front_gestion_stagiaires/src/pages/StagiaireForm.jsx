import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function StagiaireForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [form, setForm] = useState({
    nom: "",
    prenom: "",
    email: "",
    tel: "",
    dateNaiss: "",
    actif: true
  });

  // Chargement en mode EDIT
  useEffect(() => {
    if (mode === "edit") {
      api.get(`/stagiaires/${id}`).then((res) => {
        setForm(res.data);
      });
    }
  }, [id, mode]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/stagiaires/create", form);
      } else {
        await api.put(`/stagiaires/${id}`, form);
      }

      navigate("/stagiaires");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter" : "Modifier"} un stagiaire</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        
        <label>Nom</label>
        <input name="nom" value={form.nom} onChange={handleChange} required />

        <label>Prénom</label>
        <input name="prenom" value={form.prenom} onChange={handleChange} required />

        <label>Email</label>
        <input name="email" value={form.email} onChange={handleChange} required />

        <label>Téléphone</label>
        <input name="tel" value={form.tel} onChange={handleChange} />

        <label>Date de naissance</label>
        <input type="date" name="dateNaiss" value={form.dateNaiss || ""} onChange={handleChange} />

        <label>Actif</label>
        <select name="actif" value={form.actif} onChange={handleChange}>
          <option value={true}>Oui</option>
          <option value={false}>Non</option>
        </select>

        <label>Formateur</label>
        <input name="classe" value={form.Formateur} onChange={handleChange} required />

        <label>Classe</label>
        <input name="classe" value={form.classe} onChange={handleChange} required />


        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default StagiaireForm;
