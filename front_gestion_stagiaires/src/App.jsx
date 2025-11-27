import { Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage.jsx";
import StagiairesPage from "./pages/StagiairesPage.jsx";

function PrivateRoute({ children }) {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/login" replace />;
}

function App() {
  return (
    <Routes>
      {/* Page de login */}
      <Route path="/login" element={<LoginPage />} />

      {/* Page liste stagiaires (protégée) */}
      <Route
        path="/stagiaires"
        element={
          <PrivateRoute>
            <StagiairesPage />
          </PrivateRoute>
        }
      />

      {/* Redirection par défaut */}
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

export default App;
