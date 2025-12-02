import { Routes, Route, Navigate, useLocation } from "react-router-dom";
import Navbar from "./components/Navbar.jsx";
import PrivateRoute from "./components/PrivateRoute.jsx";

import LoginPage from "./pages/LoginPage.jsx";
import AccueilPage from "./pages/AccueilPage.jsx";
import StagiairesPage from "./pages/StagiairesPage.jsx";
import ProjetPage from "./pages/ProjetPage.jsx";
import StagePage from "./pages/StagePage.jsx";
import DocumentPage from "./pages/DocumentPage.jsx";
import ReunionPage from "./pages/ReunionPage.jsx";
import SearchPage from "./pages/SearchPage.jsx";

function App() {
  const location = useLocation();
  const hideNavbar = location.pathname === "/login";

  return (
    <>
      {!hideNavbar && <Navbar />}

      <Routes>
        <Route path="/login" element={<LoginPage />} />

        {/* Routes protégées */}
        <Route
          path="/accueil"
          element={<PrivateRoute><AccueilPage /></PrivateRoute>}
        />

        <Route
          path="/stagiaires"
          element={<PrivateRoute><StagiairesPage /></PrivateRoute>}
        />

        <Route
          path="/projets"
          element={<PrivateRoute><ProjetPage /></PrivateRoute>}
        />

        <Route
          path="/stages"
          element={<PrivateRoute><StagePage /></PrivateRoute>}
        />

        <Route
          path="/documents"
          element={<PrivateRoute><DocumentPage /></PrivateRoute>}
        />

        <Route
          path="/reunions"
          element={<PrivateRoute><ReunionPage /></PrivateRoute>}
        />

        <Route
          path="/recherche"
          element={<PrivateRoute><SearchPage /></PrivateRoute>}
        />

        <Route path="*" element={<Navigate to="/accueil" replace />} />
      </Routes>
    </>
  );
}

export default App;
