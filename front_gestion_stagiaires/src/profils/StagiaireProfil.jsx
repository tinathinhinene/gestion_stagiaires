import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import api from "../api/axiosClient";
import "../styles/stagiaireProfil.css";

function StagiaireProfil() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [stagiaire, setStagiaire] = useState(null);
  const [classes, setClasses] = useState([]);
  const [centres, setCentres] = useState([]);

  const [classe, setClasse] = useState(null);
  const [centre, setCentre] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const resStagiaire = await api.get(`/stagiaires/${id}`);
      const resClasses = await api.get("/classes");
      const resCentres = await api.get("/centres");

      setStagiaire(resStagiaire.data);
      setClasses(resClasses.data);
      setCentres(resCentres.data);

      // 🔥 Trouver la classe liée (SI TU UTILISES classeId côté front)
      const classeTrouvee = resClasses.data.find(
        (c) => c.stagiaireId === Number(id)
      ); 

      setClasse(classeTrouvee);

      if (classeTrouvee) {
        const centreTrouve = resCentres.data.find(
          (ctr) => ctr.id === classeTrouvee.centre?.id
        );
        setCentre(centreTrouve);
      }
    } catch (error) {
      console.error(error);
    }
  };

  if (!stagiaire) return <p>Chargement…</p>;

  return (
    <div className="page">
      <h1>
        Stagiaire : {stagiaire.prenom} {stagiaire.nom}
      </h1>

      <div className="card">
        <p><b>Prénom :</b> {stagiaire.prenom}</p>
        <p><b>Nom :</b> {stagiaire.nom}</p>
        <p><b>Email :</b> {stagiaire.email}</p>
        <p><b>Téléphone :</b> {stagiaire.tel || "—"}</p>
        <p><b>Date de naissance :</b> {stagiaire.dateNaiss}</p>

        <p><b>Adresse :</b> {stagiaire.adresse || "—"}</p>
        <p><b>Centre :</b> {centre?.nom || "—"}</p>
        <p><b>Classe :</b> {classe?.nom || "—"}</p>

        <p><b>Statut :</b> {stagiaire.actif ? "Actif" : "Inactif"}</p>
      </div>

      <button onClick={() => navigate(`/stagiaires/modifier/${id}`)}>
        Modifier
      </button>
    </div>
  );
}

export default StagiaireProfil;
