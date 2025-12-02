import { useEffect, useState } from "react";
import api from "../api/axiosClient";
import { useNavigate } from "react-router-dom";

function StagiairesPage() {
  const [stagiaires, setStagiaires] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // Déconnexion
  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };
  const handleDelete = async (id) => {
  if (!confirm("Voulez-vous vraiment supprimer ce stagiaire ?")) return;

  try {
    await api.delete(`/stagiaires/${id}`);
    setStagiaires(stagiaires.filter((s) => s.id !== id));
  } catch (err) {
    console.error(err);
    alert("Erreur lors de la suppression");
  }
};


  // Récupération des stagiaires au chargement
  useEffect(() => {
    const fetchStagiaires = async () => {
      try {
        setLoading(true);
        const response = await api.get("/stagiaires"); // ➜ http://localhost:8021/api/stagiaires
        setStagiaires(response.data);
      } catch (err) {
        console.error(err);
        setError("Impossible de charger les stagiaires (401/403 ?)");
      } finally {
        setLoading(false);
      }
    };

    fetchStagiaires();
  }, []);

  if (loading) return <p>Chargement...</p>;

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Liste des stagiaires</h1>
<button
  onClick={() => navigate("/stagiaires/new")}
  style={{ marginBottom: "1rem" }}
>
  Ajouter un stagiaire
</button>

      <button onClick={logout} style={{ marginBottom: "1rem" }}>
        Se déconnecter
      </button>

      {error && <p style={{ color: "red" }}>{error}</p>}

      {stagiaires.length === 0 ? (
        <p>Aucun stagiaire trouvé.</p>
      ) : (
        <table border="1" cellPadding="8" style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead style={{ backgroundColor: "#f1f1f1" }}>
            <tr>
              <th>ID</th>
              <th>Nom</th>
              <th>Prénom</th>
              <th>Email</th>
              <th>Téléphone</th>
              <th>Date de naissance</th>
              <th>Actif</th>
              <th>Formateur</th>
              <th>Classe</th>
            </tr>
          </thead>
         <tbody>
  {stagiaires.map((s) => (
    <tr key={s.id}>
      <td>{s.id}</td>
      <td>{s.nom}</td>
      <td>{s.prenom}</td>
      <td>{s.email}</td>
      <td>{s.tel}</td>
      <td>{s.dateNaiss || "—"}</td>
      <td>{s.actif ? "Oui" : "Non"}</td>
      <td>{s.formateur ? `${s.formateur.nom} ${s.formateur.prenom}` : "—"}</td>
      <td>{s.classe ? s.classe.nom : "—"}</td>

      {/* NOUVELLES ACTIONS CRUD */}
      <td>
        <button onClick={() => navigate(`/stagiaires/edit/${s.id}`)}>
          Modifier
        </button>
      </td>

      <td>
        <button
          style={{ color: "red" }}
          onClick={() => handleDelete(s.id)}
        >
          Supprimer
        </button>
      </td>
    </tr>
  ))}
</tbody>

        </table>
      )}
    </div>
  );
}

export default StagiairesPage;
