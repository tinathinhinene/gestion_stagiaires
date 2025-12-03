import { Link, useNavigate } from "react-router-dom";
import "../styles/navbar.css";

function Navbar() {
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="nav-left">
        <span className="nav-logo">logo</span>
      </div>

      <ul className="nav-links">
        <li><Link to="/accueil">Tableau de bord</Link></li>
        <li><Link to="/stagiaires">Stagiaires</Link></li>
        <li><Link to="/stages">Stages</Link></li>
        <li><Link to="/projets">Projets</Link></li>
        <li><Link to="/formateurs">Formateurs</Link></li>
        <li><Link to="/documents">Documents</Link></li>
      </ul>

      <div className="nav-right">
        <button className="btn-logout" onClick={logout}>Déconnexion</button>
      </div>
    </nav>
  );
}

export default Navbar;
