import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function FormateurForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [form, setForm] = useState({
    nom: "",
    prenom: "",
    email: "",
    tel: "",
    specialite: "",
    image: ""
  });

  useEffect(() => {
    if (mode === "edit") {
      api.get(`/formateurs/${id}`).then((res) => {
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
        await api.post("/formateurs/create", form);
      } else {
        await api.put(`/formateurs/${id}`, form);
      }

      navigate("/formateurs");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un formateur" : "Modifier un formateur"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        <label>Nom</label>
        <input name="nom" value={form.nom} onChange={handleChange} required />

        <label>Prénom</label>
        <input name="prenom" value={form.prenom} onChange={handleChange} required />

        <label>Email</label>
        <input name="email" value={form.email} onChange={handleChange} required />

        <label>Téléphone</label>
        <input name="tel" value={form.tel} onChange={handleChange} />

        <label>Spécialité</label>
        <input name="specialite" value={form.specialite} onChange={handleChange} />

        <label>Image (URL)</label>
        <input name="image" value={form.image} onChange={handleChange} />

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default FormateurForm;
