// src/Components/StagiaireCarousel.jsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/stagiaireCarousel.css";

function StagiaireCarousel({ stagiaires }) {
  const [index, setIndex] = useState(0);
  const navigate = useNavigate();

  const prev = () => {
    setIndex((prev) => (prev === 0 ? stagiaires.length - 1 : prev - 1));
  };

  const next = () => {
    setIndex((prev) => (prev === stagiaires.length - 1 ? 0 : prev + 1));
  };

  const getPhoto = (prenom, nom) => {
    const fileName = `${prenom}_${nom}`.toLowerCase().replaceAll(" ", "_") + ".png";
    try {
      return new URL(`../assets/images/${fileName}`, import.meta.url).href;
    } catch {
      return new URL(`../assets/images/default.png`, import.meta.url).href;
    }
  };

  return (
    <div className="carousel-container">
      
      <button className="carousel-btn" onClick={prev}>{"<"}</button>

      <div className="carousel-track">
        {stagiaires.map((s) => (
          <div
            key={s.id}
            className="bubble-container"
            onClick={() => navigate(`/stagiaires/${s.id}`)}
            style={{ cursor: "pointer" }}
          >
            <img
              className="bubble-img"
              src={getPhoto(s.prenom, s.nom)}
              alt={s.prenom}
            />
            <div className="bubble-name">{s.prenom}</div>
            <div className="bubble-lastname">{s.nom}</div>
          </div>
        ))}
      </div>

      <button className="carousel-btn" onClick={next}>{">"}</button>
    </div>
  );
}

export default StagiaireCarousel;
