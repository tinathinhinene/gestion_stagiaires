import { useEffect, useState } from "react";
import api from "../api/axiosClient";
import { useNavigate } from "react-router-dom";
import "../styles/formateurs.css";

function FormateursPage() {
  const [formateurs, setFormateurs] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    loadFormateurs();
  }, []);

  const loadFormateurs = async () => {
    try {
      const res = await api.get("/formateurs");
      setFormateurs(res.data);
    } catch (err) {
      console.error("Erreur chargement formateurs :", err);
    }
  };

  const supprimer = async (id) => {
    if (!window.confirm("Supprimer ce formateur ?")) return;

    try {
      await api.delete(`/formateurs/${id}`);
      setFormateurs((prev) => prev.filter((f) => f.id !== id));
    } catch (err) {
      console.error("Erreur suppression :", err);
      alert("Impossible de supprimer");
    }
  };

  return (
    <div className="page-container">
      <h1>Formateurs</h1>

      <div className="add-container">
        <button className="add-btn" onClick={() => navigate("/formateurs/ajouter")}>
          ➕ Ajouter un formateur
        </button>
      </div>

      <table className="formateur-table">
        <thead>
          <tr>
            <th>Nom</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {formateurs.map((f) => (
            <tr key={f.id}>
              <td>{f.prenom} {f.nom}</td>
              <td>{f.email}</td>
              <td>{f.tel || "—"}</td>

              <td>
                <button
                  className="edit-btn"
                  onClick={() => navigate(`/formateurs/modifier/${f.id}`)}
                >
                  Modifier
                </button>

                <button
                  className="delete-btn"
                  onClick={() => supprimer(f.id)}
                >
                  Supprimer
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default FormateursPage;
