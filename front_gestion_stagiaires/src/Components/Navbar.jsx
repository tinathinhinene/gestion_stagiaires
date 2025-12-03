import { Link, useNavigate } from "react-router-dom";
import "../styles/navbar.css";
import logo from "../assets/images/logo.png"; // ton logo

function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <header className="nav-header">
      <div className="nav-inner">

        {/* LOGO À GAUCHE */}
        <div className="nav-left">
          <img src={logo} alt="logo" className="nav-logo-img" />
        </div>

        {/* MENU CENTRE */}
        <nav className="nav-center">
          <Link to="/stagiaires" className="nav-link">Stagiaires</Link>
          <Link to="/accueil" className="nav-link">Tableau de bord</Link>
          <Link to="/projets" className="nav-link">Projets</Link>
          <Link to="/stages" className="nav-link">Stages</Link>
          <Link to="/documents" className="nav-link">Documents</Link>
          <Link to="/reunions" className="nav-link">Réunions</Link>
        </nav>

        {/* BARRE DE RECHERCHE + UTILISATEUR */}
        <div className="nav-right">

          {/* BARRE DE RECHERCHE */}
          <div className="nav-search">
            <input
              type="text"
              placeholder="Rechercher..."
              className="nav-search-input"
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  navigate(`/recherche?q=${e.target.value}`);
                }
              }}
            />
            <img
              src="https://cdn-icons-png.flaticon.com/512/622/622669.png"
              className="nav-search-icon"
              alt="search"
            />
          </div>

          {/* UTILISATEUR */}
      
            <div className="nav-user-info">
              <span className="nav-user-name">Connexion</span>
            </div>
          </div>

        </div>
     
    </header>
  );
}

export default Navbar;
