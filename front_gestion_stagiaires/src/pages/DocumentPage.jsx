import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function DocumentPage() {
  const navigate = useNavigate();
  const [documents, setDocuments] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchDocuments = async () => {
    try {
      const res = await api.get("/documents");
      setDocuments(res.data);
    } catch (err) {
      console.error(err);
      alert("Erreur lors du chargement des documents");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDocuments();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Supprimer ce document ?")) return;

    try {
      await api.delete(`/documents/${id}`);
      setDocuments(documents.filter((doc) => doc.id !== id));
    } catch (err) {
      console.error(err);
      alert("Impossible de supprimer le document");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Documents</h1>

      <button
        onClick={() => navigate("/documents/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Ajouter un document
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Type</th>
            <th>Chemin</th>
            <th>Date Upload</th>
            <th>Stage</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {documents.map((d) => (
            <tr key={d.id}>
              <td>{d.id}</td>
              <td>{d.nom}</td>
              <td>{d.type}</td>
              <td>{d.chemin}</td>
              <td>{d.date}</td>

              <td>
                {d.stage
                  ? `${d.stage.stagiaire?.prenom} ${d.stage.stagiaire?.nom}`
                  : "—"}
              </td>

              <td>
                <button onClick={() => navigate(`/documents/edit/${d.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  style={{ color: "red" }}
                  onClick={() => handleDelete(d.id)}
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

export default DocumentPage;
