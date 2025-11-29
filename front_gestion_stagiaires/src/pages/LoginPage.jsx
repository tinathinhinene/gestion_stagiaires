import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/axios";

function LoginPage() {
  const [nom, setNom] = useState("");
  const [motDePasse, setMotDePasse] = useState("");
  const [error, setError] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await api.post("/auth/login", {
        nom: nom,
        motDePasse: motDePasse,
      });

      const token = response.data.token;
      localStorage.setItem("token", token);

      navigate("/accueil");

    } catch (err) {
      console.error(err);
      setError("Identifiants incorrects");
    }
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h1>Connexion</h1>

      <form onSubmit={handleLogin} style={{ maxWidth: "300px" }}>
        <div>
          <label>Nom d'utilisateur</label>
          <input
            type="text"
            value={nom}
            onChange={(e) => setNom(e.target.value)}
            required
          />
        </div>

        <div style={{ marginTop: "1rem" }}>
          <label>Mot de passe</label>
          <input
            type="password"
            value={motDePasse}
            onChange={(e) => setMotDePasse(e.target.value)}
            required
          />
        </div>

        {error && <p style={{ color: "red" }}>{error}</p>}

        <button type="submit" style={{ marginTop: "1rem" }}>
          Se connecter
        </button>
      </form>
    </div>
  );
}

export default LoginPage;
