import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function CommentairePage() {
  const navigate = useNavigate();
  const [commentaires, setCommentaires] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchCommentaires = async () => {
    try {
      const res = await api.get("/commentaires");
      setCommentaires(res.data);
    } catch (err) {
      console.error(err);
      alert("Impossible de charger les commentaires");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCommentaires();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Voulez-vous supprimer ce commentaire ?")) return;

    try {
      await api.delete(`/commentaires/${id}`);
      setCommentaires(commentaires.filter((c) => c.id !== id));
    } catch (err) {
      console.error(err);
      alert("Erreur lors de la suppression");
    }
  };

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des commentaires</h1>

      <button
        onClick={() => navigate("/commentaires/new")}
        style={{ marginBottom: "1rem" }}
      >
        ➕ Ajouter un commentaire
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Message</th>
            <th>Date</th>
            <th>Formateur</th>
            <th>Projet</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {commentaires.map((c) => (
            <tr key={c.id}>
              <td>{c.id}</td>
              <td>{c.message}</td>
              <td>{c.date}</td>
              <td>
                {c.formateur
                  ? `${c.formateur.prenom} ${c.formateur.nom}`
                  : "—"}
              </td>
              <td>{c.projet ? c.projet.titre : "—"}</td>

              <td>
                <button onClick={() => navigate(`/commentaires/edit/${c.id}`)}>
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

export default CommentairePage;
