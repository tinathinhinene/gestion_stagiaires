import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axios";

function CentreForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [form, setForm] = useState({
    nom: "",
    adresse: "",
    ville: "",
    codePostal: "",
    image: ""
  });

  useEffect(() => {
    if (mode === "edit") {
      api.get(`/centres/${id}`).then((res) => {
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
        await api.post("/centres/create", form);
      } else {
        await api.put(`/centres/${id}`, form);
      }

      navigate("/centres");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un centre" : "Modifier un centre"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        <label>Nom</label>
        <input
          name="nom"
          value={form.nom}
          onChange={handleChange}
          required
        />

        <label>Adresse</label>
        <input
          name="adresse"
          value={form.adresse}
          onChange={handleChange}
          required
        />

        <label>Ville</label>
        <input
          name="ville"
          value={form.ville}
          onChange={handleChange}
        />

        <label>Adresse</label>
        <input
          name="Adresse"
          value={form.Adresse}
          onChange={handleChange}
        />

        <label>Code Postal</label>
        <input
          name="codePostal"
          value={form.codePostal}
          onChange={handleChange}
        />

        <label>Image (URL)</label>
        <input
          name="image"
          value={form.image}
          onChange={handleChange}
        />

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default CentreForm;
