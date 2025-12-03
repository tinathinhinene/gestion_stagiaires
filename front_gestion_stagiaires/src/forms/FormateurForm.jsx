import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/formateurs.css";

function FormateurForm() {
  const { id } = useParams(); // si id existe → modification
  const navigate = useNavigate();

  const [formateur, setFormateur] = useState({
    prenom: "",
    nom: "",
    email: "",
    tel: ""
  });

  useEffect(() => {
    if (id) {
      loadFormateur();
    }
  }, [id]);

  const loadFormateur = async () => {
    try {
      const res = await api.get(`/formateurs/${id}`);
      setFormateur(res.data);
    } catch (err) {
      console.error("Erreur chargement formateur :", err);
    }
  };

  const handleChange = (e) => {
    setFormateur({ ...formateur, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (id) {
        await api.put(`/formateurs/${id}`, formateur);
      } else {
        await api.post("/formateurs/create", formateur);
      }

      navigate("/formateurs");
    } catch (err) {
      console.error("Erreur sauvegarde :", err);
      alert("Impossible d'enregistrer le formateur");
    }
  };

  return (
    <div className="page-container">
      <h1>{id ? "Modifier le formateur" : "Ajouter un formateur"}</h1>

      <form className="form-container" onSubmit={handleSubmit}>
        <label>Prénom</label>
        <input
          type="text"
          name="prenom"
          value={formateur.prenom}
          onChange={handleChange}
          required
        />

        <label>Nom</label>
        <input
          type="text"
          name="nom"
          value={formateur.nom}
          onChange={handleChange}
          required
        />

        <label>Email</label>
        <input
          type="email"
          name="email"
          value={formateur.email}
          onChange={handleChange}
          required
        />

        <label>Téléphone</label>
        <input
          type="text"
          name="tel"
          value={formateur.tel}
          onChange={handleChange}
        />

        <div className="form-buttons">
          <button type="submit" className="btn-save">
            {id ? "Mettre à jour" : "Enregistrer"}
          </button>

          <button
            type="button"
            className="btn-cancel"
            onClick={() => navigate("/formateurs")}
          >
            Annuler
          </button>
        </div>
      </form>
    </div>
  );
}

export default FormateurForm;
