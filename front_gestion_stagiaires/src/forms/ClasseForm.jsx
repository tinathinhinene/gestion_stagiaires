import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/axiosClient";

function ClasseForm({ mode }) {
  const navigate = useNavigate();
  const { id } = useParams();

  const [centres, setCentres] = useState([]);

  const [form, setForm] = useState({
    nom: "",
    annee: "",
    centre: { id: null }
  });

  // Charger la liste des centres
  const fetchCentres = async () => {
    const res = await api.get("/centres");
    setCentres(res.data);
  };

  // Charger la classe si edit
  useEffect(() => {
    fetchCentres();

    if (mode === "edit") {
      api.get(`/classes/${id}`).then((res) => {
        const classe = res.data;

        setForm({
          nom: classe.nom,
          annee: classe.annee,
          centre: { id: classe.centre?.id || null }
        });
      });
    }
  }, [id, mode]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleChangeCentre = (e) => {
    setForm({ ...form, centre: { id: e.target.value } });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (mode === "add") {
        await api.post("/classes/create", form);
      } else {
        await api.put(`/classes/${id}`, form);
      }

      navigate("/classes");
    } catch (err) {
      console.error(err);
      alert("Erreur lors de l'enregistrement");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>{mode === "add" ? "Ajouter une classe" : "Modifier une classe"}</h1>

      <form onSubmit={handleSubmit} style={{ maxWidth: "300px" }}>
        <label>Nom</label>
        <input
          name="nom"
          value={form.nom}
          onChange={handleChange}
          required
        />

        <label>Année</label>
        <input
          name="annee"
          value={form.annee}
          onChange={handleChange}
        />

        <label>Centre</label>
        <select
          value={form.centre.id || ""}
          onChange={handleChangeCentre}
          required
        >
          <option value="">-- Choisir un centre --</option>
          {centres.map((c) => (
            <option key={c.id} value={c.id}>
              {c.nom}
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

export default ClasseForm;
