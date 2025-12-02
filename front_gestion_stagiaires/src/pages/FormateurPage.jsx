import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosClient";

function FormateurPage() {
  const navigate = useNavigate();
  const [formateurs, setFormateurs] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchFormateurs = async () => {
    try {
      const res = await api.get("/formateurs");
      setFormateurs(res.data);
    } catch (err) {
      console.error(err);
      alert("Impossible de charger les formateurs");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchFormateurs();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Voulez-vous vraiment supprimer ce formateur ?")) return;

    try {
      await api.delete(`/formateurs/${id}`);
      setFormateurs(formateurs.filter((f) => f.id !== id));
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la suppression");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des formateurs</h1>

      <button
        onClick={() => navigate("/formateurs/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Ajouter un formateur
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Téléphone</th>
            <th>Spécialité</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {formateurs.map((f) => (
            <tr key={f.id}>
              <td>{f.id}</td>
              <td>{f.nom}</td>
              <td>{f.prenom}</td>
              <td>{f.email}</td>
              <td>{f.tel}</td>
              <td>{f.specialite}</td>

              <td>
                <button onClick={() => navigate(`/formateurs/edit/${f.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  style={{ color: "red" }}
                  onClick={() => handleDelete(f.id)}
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

export default FormateurPage;
