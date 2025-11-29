import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { decodeToken } from "../utils/jwt";
import logo from "../assets/images/logo.png";   // <-- IMPORT DU LOGO
import "../styles/accueil.css";

function Navbar() {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const payload = decodeToken();
  const role = payload?.role ?? "inconnu";
  const username = payload?.sub || payload?.nom || "Utilisateur";

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  const toggleMenu = () => setOpen((o) => !o);

  return (
    <header className="nav-header">
      <div className="nav-inner">

        {/* -------- LOGO À GAUCHE -------- */}
        <div className="nav-left">
          <img src={logo} alt="Logo" className="nav-logo-img" />
        </div>

        {/* -------- MENU DESKTOP -------- */}
        <nav className="nav-center">
          <NavLink to="/accueil" className="nav-link">
            Tableau de bord
          </NavLink>
          <NavLink to="/stagiaires" className="nav-link">
            Stagiaires
          </NavLink>
          <NavLink to="/projets" className="nav-link">
            Projets
          </NavLink>
          <NavLink to="/stages" className="nav-link">
            Stages
          </NavLink>
          <NavLink to="/documents" className="nav-link">
            Documents
          </NavLink>
          <NavLink to="/reunions" className="nav-link">
            Réunions
          </NavLink>
        </nav>

        {/* -------- ZONE UTILISATEUR (DESKTOP) -------- */}
        <div className="nav-right">
          <div className="nav-user">
            <div className="nav-avatar">
              {username.charAt(0).toUpperCase()}
            </div>
            <div className="nav-user-info">
              <span className="nav-user-name">{username}</span>
              <span className="nav-user-role">
                {role === "admin" ? "Administrateur" : "Formateur"}
              </span>
            </div>
          </div>

          <button className="nav-logout-btn" onClick={handleLogout}>
            Déconnexion
          </button>

          {/* Burger menu (mobile) */}
          <button className="nav-burger" onClick={toggleMenu}>
            <span />
            <span />
            <span />
          </button>
        </div>
      </div>

      {/* -------- MENU MOBILE -------- */}
      {open && (
        <nav className="nav-mobile">
          <NavLink to="/accueil" className="nav-mobile-link" onClick={toggleMenu}>
            Tableau de bord
          </NavLink>
          <NavLink to="/stagiaires" className="nav-mobile-link" onClick={toggleMenu}>
            Stagiaires
          </NavLink>
          <NavLink to="/projets" className="nav-mobile-link" onClick={toggleMenu}>
            Projets
          </NavLink>
          <NavLink to="/stages" className="nav-mobile-link" onClick={toggleMenu}>
            Stages
          </NavLink>
          <NavLink to="/documents" className="nav-mobile-link" onClick={toggleMenu}>
            Documents
          </NavLink>
          <NavLink to="/reunions" className="nav-mobile-link" onClick={toggleMenu}>
            Réunions
          </NavLink>

          <button className="nav-mobile-logout" onClick={handleLogout}>
            Déconnexion
          </button>
        </nav>
      )}
    </header>
  );
}

export default Navbar;
