import { useEffect, useState, useContext } from "react";
import api from "../api/axiosClient";
import { AuthContext } from "../context/AuthContext";

import StatCard from "../components/StatCard";
import TimelineBar from "../components/TimelineBar";
import StagiaireCard from "../components/StagiaireCard";

import "../styles/accueil.css";

function AccueilPage() {
  const { user } = useContext(AuthContext); // qui est connecté (admin / formateur)

  const [role, setRole] = useState(null);
  const [stagiaires, setStagiaires] = useState([]);
  const [stages, setStages] = useState([]);
  const [reunions, setReunions] = useState([]);

  // 🟦 Quand l'utilisateur est connu → on charge les données
  useEffect(() => {
    if (!user) return;

    setRole(user.role);
    loadData();
  }, [user]);

  // 🟩 Charge les données SANS se soucier du rôle
  // 👉 le backend se charge de filtrer en fonction du token
  const loadData = async () => {
    try {
      const [resStagiaires, resStages, resReunions] = await Promise.all([
        api.get("/stagiaires"),
        api.get("/stages"),
        api.get("/reunions"),
      ]);

      setStagiaires(resStagiaires.data);
      setStages(resStages.data);
      setReunions(resReunions.data);
    } catch (err) {
      console.error("Erreur lors du chargement des données :", err);
    }
  };

  return (
    <div className="accueil-wrapper">
      <div className="accueil-container">
        {/* CARTES DE STATS */}
        <div className="stats-box">
          <StatCard title="Stages en cours" value={stages.length} />
          <StatCard title="Stagiaires actifs" value={stagiaires.length} />
          <StatCard title="Réunions à venir" value={reunions.length} />
        </div>

        {/* TIMELINE DES STAGES */}
        <h2 className="section-title">Tableau de bord</h2>
        <div className="timeline-list">
          {stages.map((s) => (
            <TimelineBar
              key={s.id}
              nom={`${s.stagiaire.prenom} ${s.stagiaire.nom}`}
              debut={s.dateDebut}
              fin={s.dateFin}
            />
          ))}
        </div>

        {/* LISTE DES STAGIAIRES */}
        <h2 className="section-title">Stagiaires</h2>
        <div className="stagiaire-grid">
          {stagiaires.map((st) => (
            <StagiaireCard
              key={st.id}
              nom={st.nom}
              prenom={st.prenom}
              photo={st.photo}
            />
          ))}
        </div>
      </div>
    </div>
  );
}

export default AccueilPage;
