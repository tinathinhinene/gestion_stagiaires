// src/pages/StagiaireProfil.jsx
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import api from "../api/axiosClient";
import "../styles/StagiaireProfil.css";

function StagiaireProfil() {
  const { id } = useParams();
  const [stagiaire, setStagiaire] = useState(null);

  useEffect(() => {
    api.get(`/stagiaires/${id}`).then((res) => setStagiaire(res.data));
  }, [id]);

  if (!stagiaire) return <p>Chargement...</p>;

  return (
    <div className="profil-container">
      <h1>{stagiaire.prenom} {stagiaire.nom}</h1>

      <p>Email : {stagiaire.email}</p>
      <p>Téléphone : {stagiaire.tel}</p>
      <p>Date de naissance : {stagiaire.dateNaiss}</p>
      <p>État : {stagiaire.actif ? "Actif" : "Inactif"}</p>
    </div>
  );
}

export default StagiaireProfil;
