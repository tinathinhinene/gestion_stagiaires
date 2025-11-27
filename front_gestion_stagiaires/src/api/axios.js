import axios from "axios";

// URL de base récupérée depuis .env
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
});

// Interceptor pour ajouter automatiquement le token JWT si présent
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token"); // on stockera le token ici après login
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
