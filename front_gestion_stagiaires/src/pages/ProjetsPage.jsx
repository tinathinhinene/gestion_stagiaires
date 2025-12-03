import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosClient";

function ProjetPage() {
  const navigate = useNavigate();
  const [projets, setProjets] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchProjets = async () =>
  {
    try {
      const res = await api.get("/projets");
      setProjets(res.data);
    } catch (err) {
      console.error(err);
      alert("Impossible de charger les projets");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProjets();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Voulez-vous supprimer ce projet ?")) return;

    try {
      await api.delete(`/projets/${id}`);
      setProjets(projets.filter((p) => p.id !== id));
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la suppression");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des projets</h1>

      <button
        onClick={() => navigate("/projets/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Ajouter un projet
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Titre</th>
            <th>Description</th>
            <th>Avancement</th>
            <th>Note</th>
            <th>Stage</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {projets.map((p) => (
            <tr key={p.id}>
              <td>{p.id}</td>
              <td>{p.titre}</td>
              <td>{p.description}</td>
              <td>{p.avancement}%</td>
              <td>{p.note}</td>
              <td>
                {p.stage
                  ? `${p.stage.stagiaire.prenom} ${p.stage.stagiaire.nom}`
                  : "—"}
              </td>

              <td>
                <button onClick={() => navigate(`/projets/edit/${p.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  style={{ color: "red" }}
                  onClick={() => handleDelete(p.id)}
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

export default ProjetPage;
