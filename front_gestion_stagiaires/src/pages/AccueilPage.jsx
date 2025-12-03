import { useEffect, useState, useContext } from "react";
import api from "../api/axiosClient";
import { AuthContext } from "../context/AuthContext";

import StatCard from "../components/StatCard";
import Timeline from "../components/Timeline";

import "../styles/accueil.css";
import "../styles/timeline.css";

function AccueilPage() {
  const { user } = useContext(AuthContext);

  const [stagiaires, setStagiaires] = useState([]);
  const [stages, setStages] = useState([]);
  const [reunions, setReunions] = useState([]);

  // Année affichée dans le Gantt
  const [year, setYear] = useState(2025);

  useEffect(() => {
    if (!user) return;
    loadData();
  }, [user]);

  const loadData = async () => {
    try {
      // ✅ TON BACK GÈRE DÉJÀ LES DROITS (admin/formateur)
      // donc on ne refiltre PLUS côté front
      const [resStagiaires, resStages, resReunions] = await Promise.all([
        api.get("/stagiaires"),
        api.get("/stages"),
        api.get("/reunions"),
      ]);

      setStagiaires(resStagiaires.data);
      setStages(resStages.data);
      setReunions(resReunions.data);
    } catch (err) {
      console.error("Erreur chargement :", err);
    }
  };

  // 🔥 Filtrage PRO pour l’année (stages multi-années OK)
  const stagesFiltered = stages.filter((s) => {
    const d1 = new Date(s.dateDebut);
    const d2 = new Date(s.dateFin);

    // Début et fin de l'année affichée
    const startYear = new Date(year, 0, 1);
    const endYear = new Date(year, 11, 31, 23, 59, 59);

    // Le stage est affiché s'il coupe l'année
    return d2 >= startYear && d1 <= endYear;
  });

  return (
    <div className="accueil-wrapper">
      <div className="accueil-container">
        {/* STATISTIQUES */}
        <div className="stats-box">
          <StatCard title="Stages en cours" value={stages.length} />
          <StatCard title="Stagiaires actifs" value={stagiaires.length} />
          <StatCard title="Réunions à venir" value={reunions.length} />
        </div>

        <h2 className="section-title">Tableau de bord</h2>

        {/* TIMELINE GANTT */}
        <Timeline
          year={year}
          setYear={setYear}
          stages={stagesFiltered}
        />
      </div>
    </div>
  );
}

export default AccueilPage;
