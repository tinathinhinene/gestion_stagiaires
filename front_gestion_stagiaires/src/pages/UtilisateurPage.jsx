import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function UtilisateurPage() {
  const navigate = useNavigate();
  const [utilisateurs, setUtilisateurs] = useState([]);

  const fetchUtilisateurs = async () => {
    try {
      const res = await api.get("/utilisateurs");
      setUtilisateurs(res.data);
    } catch (err) {
      console.error(err);
      alert("Erreur lors du chargement des utilisateurs");
    }
  };

  useEffect(() => {
    fetchUtilisateurs();
  }, []);

  const handleDelete = async (id) => {
    if (!confirm("Supprimer cet utilisateur ?")) return;

    try {
      await api.delete(`/utilisateurs/${id}`);
      setUtilisateurs(utilisateurs.filter((u) => u.id !== id));
    } catch (err) {
      console.error(err);
      alert("Impossible de supprimer");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Utilisateurs</h1>

      <button onClick={() => navigate("/utilisateurs/new")}>
        ➕ Nouvel utilisateur
      </button>

      <table border="1" cellPadding="8" style={{ width: "100%", marginTop: "1rem" }}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nom utilisateur</th>
            <th>Rôle</th>
            <th>Formateur lié</th>
            <th>Actions</th>
          </tr>
        </thead>

        <tbody>
          {utilisateurs.map((u) => (
            <tr key={u.id}>
              <td>{u.id}</td>
              <td>{u.nom}</td>
              <td>{u.role}</td>
              <td>
                {u.formateur
                  ? `${u.formateur.prenom} ${u.formateur.nom}`
                  : "—"}
              </td>

              <td>
                <button onClick={() => navigate(`/utilisateurs/edit/${u.id}`)}>
                  Modifier
                </button>
                &nbsp;
                <button
                  style={{ color: "red" }}
                  onClick={() => handleDelete(u.id)}
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

export default UtilisateurPage;
