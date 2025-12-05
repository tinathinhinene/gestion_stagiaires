import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/documents.css";

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
    if (!window.confirm("Supprimer ce document ?")) return;

    try {
      await api.delete(`/documents/${id}`);
      setDocuments((prev) => prev.filter((d) => d.id !== id));
    } catch (err) {
      console.error(err);
      alert("Impossible de supprimer le document");
    }
  };

  if (loading) return <p className="loading">Chargement...</p>;

  return (
    <div className="documents-container">
      <h1 className="documents-title">Documents des stagiaires</h1>

      <button
        className="doc-add-btn"
        onClick={() => navigate("/documents/new")}
      >
        ➕ Ajouter un document
      </button>

      <table className="documents-table">
        <thead>
          <tr>
            <th>Nom du stagiaire</th>
            <th>Nom du document</th>
            <th>Type</th>
            <th>Chemin</th>
            <th>Date</th>
            <th>Fichier</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {documents.map((d) => (
            <tr key={d.id}>
              <td>
                {d.stage?.stagiaire
                  ? `${d.stage.stagiaire.prenom} ${d.stage.stagiaire.nom}`
                  : "—"}
              </td>

              <td>{d.nom}</td>
              <td>{d.type}</td>
              <td>{d.chemin}</td>
              <td>{new Date(d.date).toLocaleDateString("fr-FR")}</td>

              {/* Bouton Télécharger */}
              <td>
                <a
                  href={`http://localhost:8021/${d.chemin}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="download-link"
                >
                  télécharger ⬇️
                </a>
              </td>

              {/* Actions */}
              <td>
                <button
                  className="doc-edit-btn"
                  onClick={() => navigate(`/documents/edit/${d.id}`)}
                >
                  Modifier
                </button>

                <button
                  className="doc-delete-btn"
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
