import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function CentrePage() {
  const navigate = useNavigate();
  const [centres, setCentres] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchCentres = async () => {
    try {
      const res = await api.get("/centres");
      setCentres(res.data);
    } catch (err) {
      console.error(err);
      alert("Impossible de charger les centres");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCentres();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Voulez-vous vraiment supprimer ce centre ?")) return;

    try {
      await api.delete(`/centres/${id}`);
      setCentres(centres.filter((c) => c.id !== id));
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la suppression");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des centres</h1>

      <button
        onClick={() => navigate("/centres/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Ajouter un centre
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Adresse</th>
            <th>Ville</th>
            <th>Code Postal</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {centres.map((c) => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>{c.nom}</td>
              <td>{c.adresse}</td>
              <td>{c.ville}</td>
              <td>{c.codePostal}</td>
              
              <td>
                <button onClick={() => navigate(`/centres/edit/${c.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  style={{ color: "red" }}
                  onClick={() => handleDelete(c.id)}
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

export default CentrePage;
