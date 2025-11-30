import { useEffect, useState } from "react";
import api from "../api/axios";
import { decodeToken } from "../utils/jwt";

import StatCard from "../components/StatCard";
import TimelineBar from "../components/TimelineBar";
import StagiaireCard from "../components/StagiaireCard";

import "../styles/accueil.css";

function AccueilPage() {
  const [role, setRole] = useState(null);
  const [stagiaires, setStagiaires] = useState([]);
  const [stages, setStages] = useState([]);
  const [reunions, setReunions] = useState([]);

  useEffect(() => {
    const payload = decodeToken();
    if (!payload) return;

    setRole(payload.role);
    loadData(payload);
  }, []);

  const loadData = async (payload) => {
    try {
      if (payload.role === "admin") {
        setStagiaires((await api.get("/stagiaires")).data);
        setStages((await api.get("/stages")).data);
        setReunions((await api.get("/reunions")).data);
      }

      if (payload.role === "formateur") {
        const idFor = payload.formateurId;
        setStagiaires((await api.get(`/formateurs/${idFor}/stagiaires`)).data);
        setStages((await api.get(`/formateurs/${idFor}/stages`)).data);
        setReunions((await api.get(`/formateurs/${idFor}/reunions`)).data);
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="accueil-wrapper">
      {/* ❌ SUPPRESSION DEFINITIVE DU NAVBAR ICI */}

      <div className="accueil-container">
        <div className="stats-box">
          <StatCard title="Stages en cours" value={stages.length} />
          <StatCard title="Stagiaires actifs" value={stagiaires.length} />
          <StatCard title="Réunions à venir" value={reunions.length} />
        </div>

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
