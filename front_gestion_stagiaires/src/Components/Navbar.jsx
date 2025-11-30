import { useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { decodeToken } from "../utils/jwt";
import "../styles/accueil.css";
import logo from "../assets/images/logo.png";

function Navbar() {
  const [open, setOpen] = useState(false);
  const [searchOpen, setSearchOpen] = useState(false);
  const [query, setQuery] = useState("");

  const navigate = useNavigate();

  const payload = decodeToken();
  const role = payload?.role ?? "inconnu";
  const username = payload?.sub || payload?.nom || "Utilisateur";

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  const toggleMenu = () => setOpen((o) => !o);

  // 👉 Fonction qui lance la recherche
  const launchSearch = () => {
    const q = query.trim();
    if (!q) return; // si vide, on ne fait rien

    // On va vers la page /recherche avec le paramètre ?q=
    navigate(`/recherche?q=${encodeURIComponent(q)}`);

    // on ferme la section détaillée
    setSearchOpen(false);
  };

  return (
    <header className="nav-header">
      <div className="nav-inner">
        {/* LOGO */}
        <div className="nav-left">
          <img
            src={logo}
            alt="DEVISSE - Gestion des stagiaires"
            className="nav-logo-img"
          />
        </div>

        {/* MENU CENTRE */}
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

        {/* BARRE DE RECHERCHE (dans la navbar) */}
        <div className="nav-search">
          <input
            type="text"
            className="nav-search-input"
            placeholder="Rechercher..."
            value={query} // 🔹 lié à l'état query
            onChange={(e) => setQuery(e.target.value)}
            onClick={() => setSearchOpen(true)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                launchSearch();
              }
            }}
          />
          <img
            src="https://static.vecteezy.com/ti/vecteur-libre/p1/4897827-loupe-ou-recherche-icone-plat-vecteur-graphique-sur-fond-isole-gratuit-vectoriel.jpg"
            className="nav-search-icon"
            alt="Recherche"
            onClick={() => setSearchOpen(true)}
          />
        </div>

        {/* UTILISATEUR */}
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

          <button className="nav-burger" onClick={toggleMenu}>
            <span />
            <span />
            <span />
          </button>
        </div>
      </div>

      {/* MENU MOBILE */}
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

      {/* SECTION RECHERCHE DÉTAILLÉE */}
      {searchOpen && (
        <div className="search-section">
          <div className="search-content">
            <h2>Recherche détaillée</h2>

            <input
              type="text"
              className="search-input"
              placeholder="Tapez votre recherche..."
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  launchSearch();
                }
              }}
            />

            <button onClick={launchSearch}>
              Lancer la recherche
            </button>
          </div>
        </div>
      )}
    </header>
  );
}

export default Navbar;
