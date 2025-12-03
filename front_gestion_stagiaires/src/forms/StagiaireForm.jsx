import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/stagiaireForm.css";

function StagiaireForm({ mode = "add" }) {
  const navigate = useNavigate();
  const { id } = useParams();

  // 🌿 État du formulaire
  const [form, setForm] = useState({
    prenom: "",
    nom: "",
    email: "",
    tel: "",
    dateNaiss: "",
    adresse: "",
    centre: "",
    classe: "",
    statut: "",
    nomDocument: "",
    typeDocument: "",
    dateTelechargement: "",
    nomProjet: "",
    description: "",
    actif: true,
  });

  // 🌿 Chargement en mode EDIT
  useEffect(() => {
    if (mode === "edit" && id) {
      api
        .get(`/stagiaires/${id}`)
        .then((res) => {
          const s = res.data;
          setForm((prev) => ({
            ...prev,
            prenom: s.prenom || "",
            nom: s.nom || "",
            email: s.email || "",
            tel: s.tel || "",
            dateNaiss: s.dateNaiss || "",
            actif: s.actif ?? true,
          }));
        })
        .catch((err) => {
          console.error("Erreur lors du chargement du stagiaire :", err);
          alert("Impossible de charger le stagiaire.");
        });
    }
  }, [mode, id]);

  // 🌿 Gestion des champs
  const handleChange = (e) => {
    const { name, type, value, checked } = e.target;
    setForm((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  // 🌿 Soumission du formulaire
  const handleSubmit = async (e) => {
    e.preventDefault();

    // On envoie uniquement les champs que le backend connaît
    const payload = {
      nom: form.nom,
      prenom: form.prenom,
      email: form.email,
      tel: form.tel,
      dateNaiss: form.dateNaiss,
      actif: form.actif,
    };

    try {
      if (mode === "add") {
        // ✅ Création avec ton endpoint existant
        await api.post("/stagiaires/create", payload);
      } else {
        // ✅ Modification
        await api.put(`/stagiaires/${id}`, payload);
      }

      navigate("/stagiaires");
    } catch (err) {
      console.error("Erreur enregistrement stagiaire :", err);

      const msg =
        err.response?.data?.message ||
        err.response?.data ||
        "Erreur lors de l’enregistrement du stagiaire.";
      alert(msg);
    }
  };

  return (
    <div className="form-page">
      <h1 className="form-title">
        {mode === "add" ? "Ajouter un stagiaire" : "Modifier un stagiaire"}
      </h1>

      <form className="form-container" onSubmit={handleSubmit}>
        {/* LIGNE 1 */}
        <div className="form-row">
          <input
            name="prenom"
            placeholder="Prénom"
            value={form.prenom}
            onChange={handleChange}
            required
          />
          <input
            name="nom"
            placeholder="Nom"
            value={form.nom}
            onChange={handleChange}
            required
          />
          <input
            type="email"
            name="email"
            placeholder="Email"
            value={form.email}
            onChange={handleChange}
            required
          />
        </div>

        {/* LIGNE 2 */}
        <div className="form-row">
          <input
            name="tel"
            placeholder="Téléphone"
            value={form.tel}
            onChange={handleChange}
          />
          <input
            type="date"
            name="dateNaiss"
            value={form.dateNaiss || ""}
            onChange={handleChange}
          />
          <input
            name="adresse"
            placeholder="Adresse"
            value={form.adresse}
            onChange={handleChange}
          />
        </div>

        {/* LIGNE 3 */}
        <div className="form-row">
          <input
            name="centre"
            placeholder="Centre"
            value={form.centre}
            onChange={handleChange}
          />
          <input
            name="classe"
            placeholder="Classe"
            value={form.classe}
            onChange={handleChange}
          />
          <input
            name="statut"
            placeholder="Statut"
            value={form.statut}
            onChange={handleChange}
          />
        </div>

        {/* LIGNE 4 */}
        <div className="form-row">
          <input
            name="nomDocument"
            placeholder="Nom du document"
            value={form.nomDocument}
            onChange={handleChange}
          />
          <input
            name="typeDocument"
            placeholder="Type du document"
            value={form.typeDocument}
            onChange={handleChange}
          />
          <input
            type="date"
            name="dateTelechargement"
            value={form.dateTelechargement || ""}
            onChange={handleChange}
          />
        </div>

        {/* NOM PROJET */}
        <div className="form-row">
          <input
            name="nomProjet"
            placeholder="Nom du projet"
            value={form.nomProjet}
            onChange={handleChange}
            style={{ gridColumn: "1 / 4" }}
          />
        </div>

        {/* DESCRIPTION */}
        <textarea
          className="big-textarea"
          name="description"
          placeholder="Description"
          value={form.description}
          onChange={handleChange}
        />

        {/* ACTIF */}
        <label className="checkbox-zone">
          <input
            type="checkbox"
            name="actif"
            checked={form.actif}
            onChange={handleChange}
          />
          Actif
        </label>

        {/* BOUTON */}
        <button type="submit" className="save-btn">
          Enregistrer
        </button>
      </form>
    </div>
  );
}

export default StagiaireForm;
