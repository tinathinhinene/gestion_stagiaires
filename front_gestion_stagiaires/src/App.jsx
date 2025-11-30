import { Routes, Route, Navigate, useLocation } from "react-router-dom";

import LoginPage from "./pages/LoginPage.jsx";
import AccueilPage from "./pages/AccueilPage.jsx";
import StagiairesPage from "./pages/StagiairesPage.jsx";
import StagiaireForm from "./pages/StagiaireForm.jsx";
import CentrePage from "./pages/CentrePage.jsx";
import CentreForm from "./pages/CentreForm.jsx";
import ClassePage from "./pages/ClassePage.jsx";
import ClasseForm from "./pages/ClasseForm.jsx";
import FormateurPage from "./pages/FormateurPage.jsx";
import FormateurForm from "./pages/FormateurForm.jsx";
import ProjetPage from "./pages/ProjetPage.jsx";
import ProjetForm from "./pages/ProjetForm.jsx";
import StagePage from "./pages/StagePage.jsx";
import StageForm from "./pages/StageForm.jsx";
import CommentairePage from "./pages/CommentairePage.jsx";
import CommentaireForm from "./pages/CommentaireForm.jsx";
import ReunionPage from "./pages/ReunionPage.jsx";
import ReunionForm from "./pages/ReunionForm.jsx";
import DocumentPage from "./pages/DocumentPage.jsx";
import DocumentForm from "./pages/DocumentForm.jsx";
import UtilisateurPage from "./pages/UtilisateurPage.jsx";
import UtilisateurForm from "./pages/UtilisateurForm.jsx";
import SearchPage from "./pages/SearchPage.jsx";

import Navbar from "./components/Navbar.jsx";

// ===== Route protégée =====
function PrivateRoute({ children }) {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/login" replace />;
}

function App() {
  const location = useLocation();
  const isLogin = location.pathname === "/login";

  return (
    <>
      {/* ✅ Navbar affichée partout SAUF sur /login */}
      {!isLogin && <Navbar />}

      <Routes>
        {/* Login */}
        <Route path="/login" element={<LoginPage />} />

        {/* Accueil */}
        <Route
          path="/accueil"
          element={
            <PrivateRoute>
              <AccueilPage />
            </PrivateRoute>
          }
        />

        {/* Stagiaires */}
        <Route
          path="/stagiaires"
          element={
            <PrivateRoute>
              <StagiairesPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/stagiaires/new"
          element={
            <PrivateRoute>
              <StagiaireForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/stagiaires/edit/:id"
          element={
            <PrivateRoute>
              <StagiaireForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Centres */}
        <Route
          path="/centres"
          element={
            <PrivateRoute>
              <CentrePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/centres/new"
          element={
            <PrivateRoute>
              <CentreForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/centres/edit/:id"
          element={
            <PrivateRoute>
              <CentreForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Classes */}
        <Route
          path="/classes"
          element={
            <PrivateRoute>
              <ClassePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/classes/new"
          element={
            <PrivateRoute>
              <ClasseForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/classes/edit/:id"
          element={
            <PrivateRoute>
              <ClasseForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Formateurs */}
        <Route
          path="/formateurs"
          element={
            <PrivateRoute>
              <FormateurPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/formateurs/new"
          element={
            <PrivateRoute>
              <FormateurForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/formateurs/edit/:id"
          element={
            <PrivateRoute>
              <FormateurForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Projets */}
        <Route
          path="/projets"
          element={
            <PrivateRoute>
              <ProjetPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/projets/new"
          element={
            <PrivateRoute>
              <ProjetForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/projets/edit/:id"
          element={
            <PrivateRoute>
              <ProjetForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Stages */}
        <Route
          path="/stages"
          element={
            <PrivateRoute>
              <StagePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/stages/new"
          element={
            <PrivateRoute>
              <StageForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/stages/edit/:id"
          element={
            <PrivateRoute>
              <StageForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Commentaires */}
        <Route
          path="/commentaires"
          element={
            <PrivateRoute>
              <CommentairePage />
            </PrivateRoute>
          }
        />
        <Route
          path="/commentaires/new"
          element={
            <PrivateRoute>
              <CommentaireForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/commentaires/edit/:id"
          element={
            <PrivateRoute>
              <CommentaireForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Réunions */}
        <Route
          path="/reunions"
          element={
            <PrivateRoute>
              <ReunionPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/reunions/new"
          element={
            <PrivateRoute>
              <ReunionForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/reunions/edit/:id"
          element={
            <PrivateRoute>
              <ReunionForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Documents */}
        <Route
          path="/documents"
          element={
            <PrivateRoute>
              <DocumentPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/documents/new"
          element={
            <PrivateRoute>
              <DocumentForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/documents/edit/:id"
          element={
            <PrivateRoute>
              <DocumentForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* Utilisateurs */}
        <Route
          path="/utilisateurs"
          element={
            <PrivateRoute>
              <UtilisateurPage />
            </PrivateRoute>
          }
        />
        <Route
          path="/utilisateurs/new"
          element={
            <PrivateRoute>
              <UtilisateurForm mode="add" />
            </PrivateRoute>
          }
        />
        <Route
          path="/utilisateurs/edit/:id"
          element={
            <PrivateRoute>
              <UtilisateurForm mode="edit" />
            </PrivateRoute>
          }
        />

        {/* 🔍 Recherche */}
        <Route
          path="/recherche"
          element={
            <PrivateRoute>
              <SearchPage />
            </PrivateRoute>
          }
        />

        {/* Redirection par défaut */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </>
  );
}

export default App;
