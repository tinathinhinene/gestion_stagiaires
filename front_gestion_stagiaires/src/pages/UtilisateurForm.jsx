import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";

function UtilisateurForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [formateurs, setFormateurs] = useState([]);

  const [form, setForm] = useState({
    nom: "",
    motDePasse: "",
    role: "admin",
    formateurId: ""
  });

  // Charger les formateurs pour le select
  const fetchFormateurs = async () => {
    const res = await api.get("/formateurs");
    setFormateurs(res.data);
  };

  // Chargement édition
  useEffect(() => {
    fetchFormateurs();

    if (mode === "edit") {
      api.get(`/utilisateurs/${id}`).then((res) => {
        const u = res.data;

        setForm({
          nom: u.nom,
          motDePasse: "",
          role: u.role,
          formateurId: u.formateur?.id || ""
        });
      });
    }
  }, [mode, id]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/utilisateurs/create", {
          nom: form.nom,
          motDePasse: form.motDePasse,
          role: form.role,
          formateur: form.role === "formateur" ? { id: form.formateurId } : null
        });
      } else {
        await api.put(`/utilisateurs/${id}`, {
          nom: form.nom,
          motDePasse: form.motDePasse !== "" ? form.motDePasse : undefined,
          role: form.role,
          formateur: form.role === "formateur" ? { id: form.formateurId } : null
        });
      }

      navigate("/utilisateurs");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Nouvel utilisateur" : "Modifier utilisateur"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        <label>Nom utilisateur</label>
        <input
          name="nom"
          value={form.nom}
          onChange={handleChange}
          required
        />

        <label>Mot de passe</label>
        <input
          type="password"
          name="motDePasse"
          value={form.motDePasse}
          onChange={handleChange}
          placeholder={mode === "edit" ? "(laisser vide pour ne pas changer)" : ""}
          required={mode === "add"}
        />

        <label>Rôle</label>
        <select name="role" value={form.role} onChange={handleChange}>
          <option value="admin">ADMIN</option>
          <option value="formateur">FORMATEUR</option>
        </select>

        {form.role === "formateur" && (
          <>
            <label>Associer à un formateur</label>
            <select
              name="formateurId"
              value={form.formateurId}
              onChange={handleChange}
              required
            >
              <option value="">-- Choisir un formateur --</option>
              {formateurs.map((f) => (
                <option key={f.id} value={f.id}>
                  {f.prenom} {f.nom}
                </option>
              ))}
            </select>
          </>
        )}

        <button type="submit" style={{ marginTop: "1rem" }}>
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default UtilisateurForm;
