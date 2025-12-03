import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";

function StageForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [stagiaires, setStagiaires] = useState([]);
  const [form, setForm] = useState({
    dateDebut: "",
    dateFin: "",
    etat: "en_cours",
    conventionSignee: false,
    attestationLivree: false,
    stagiaire: { id: "" }
  });

  const fetchStagiaires = async () => {
    const res = await api.get("/stagiaires");
    setStagiaires(res.data);
  };

  useEffect(() => {
    fetchStagiaires();

    if (mode === "edit") {
      api.get(`/stages/${id}`).then((res) => {
        const s = res.data;

        setForm({
          dateDebut: s.dateDebut,
          dateFin: s.dateFin,
          etat: s.etat,
          conventionSignee: s.conventionSignee,
          attestationLivree: s.attestationLivree,
          stagiaire: { id: s.stagiaire?.id || "" }
        });
      });
    }
  }, [id, mode]);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;

    setForm({
      ...form,
      [name]: type === "checkbox" ? checked : value
    });
  };

  const handleStagiaireChange = (e) => {
    setForm({ ...form, stagiaire: { id: e.target.value } });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/stages", form);
      } else {
        await api.put(`/stages/${id}`, form);
      }

      navigate("/stages");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la sauvegarde du stage");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter un stage" : "Modifier un stage"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        
        <label>Date début</label>
        <input
          type="date"
          name="dateDebut"
          value={form.dateDebut}
          onChange={handleChange}
          required
        />

        <label>Date fin</label>
        <input
          type="date"
          name="dateFin"
          value={form.dateFin}
          onChange={handleChange}
          required
        />

        <label>État</label>
        <select name="etat" value={form.etat} onChange={handleChange}>
          <option value="en_cours">En cours</option>
          <option value="termine">Terminé</option>
          <option value="en_attente">En attente</option>
        </select>

        <label>
          <input
            type="checkbox"
            name="conventionSignee"
            checked={form.conventionSignee}
            onChange={handleChange}
          />
          Convention signée
        </label>

        <label>
          <input
            type="checkbox"
            name="attestationLivree"
            checked={form.attestationLivree}
            onChange={handleChange}
          />
          Attestation livrée
        </label>

        <label>Stagiaire</label>
        <select value={form.stagiaire.id} onChange={handleStagiaireChange}>
          <option value="">-- Choisir un stagiaire --</option>
          {stagiaires.map((s) => (
            <option key={s.id} value={s.id}>
              {s.prenom} {s.nom}
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

export default StageForm;
