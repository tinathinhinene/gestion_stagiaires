import { Routes, Route, Navigate } from "react-router-dom";

// NAVBAR
import Navbar from "./components/Navbar";

// PAGES
import LoginPage from "./pages/LoginPage";
import AccueilPage from "./pages/AccueilPage";
import StagiairesPage from "./pages/StagiairesPage";
import FormateursPage from "./pages/FormateursPage";
import DocumentsPage from "./pages/DocumentsPage";
import ReunionPage from "./pages/ReunionsPage";
import SearchPage from "./pages/SearchPage";

// FORMS
import StagiaireForm from "./forms/StagiaireForm";
import FormateurForm from "./forms/FormateurForm";
import StageForm from "./forms/StageForm";         // 👉 Garde ce fichier : il sert pour créer un stage depuis le stagiaire
import DocumentForm from "./forms/DocumentForm";
import ReunionForm from "./forms/ReunionForm";
import ProjetForm from "./forms/ProjetForm";

// PROFILS
import StagiaireProfil from "./profils/StagiaireProfil";
import FormateurProfil from "./profils/FormateurProfil";

function App() {
  return (
    <>
      <Navbar />

      <Routes>
        {/* Redirection route racine */}
        <Route path="/" element={<Navigate to="/accueil" />} />

        {/* Auth */}
        <Route path="/login" element={<LoginPage />} />

        {/* Accueil */}
        <Route path="/accueil" element={<AccueilPage />} />

        {/* ====================== STAGIAIRES ====================== */}
        <Route path="/stagiaires" element={<StagiairesPage />} />
        <Route path="/stagiaires/ajouter" element={<StagiaireForm />} />
        <Route path="/stagiaires/modifier/:id" element={<StagiaireForm />} />
        <Route path="/stagiaires/:id" element={<StagiaireProfil />} />

        {/* ====================== FORMATEURS ====================== */}
        <Route path="/formateurs" element={<FormateursPage />} />
        <Route path="/formateurs/ajouter" element={<FormateurForm />} />
        <Route path="/formateurs/modifier/:id" element={<FormateurForm />} />
        <Route path="/formateurs/:id" element={<FormateurProfil />} />

        {/* ====================== STAGES (FORM ONLY) =============== */}
        {/* Pas de StagesPage → on garde le formulaire */}
        <Route path="/stages/ajouter" element={<StageForm />} />
        <Route path="/stages/modifier/:id" element={<StageForm />} />

        {/* ====================== PROJETS (FORM ONLY) ============== */}
        <Route path="/projets/ajouter" element={<ProjetForm />} />
        <Route path="/projets/modifier/:id" element={<ProjetForm />} />

        {/* ====================== DOCUMENTS ========================= */}
        <Route path="/documents" element={<DocumentsPage />} />
        <Route path="/documents/ajouter" element={<DocumentForm />} />

        {/* ====================== RÉUNIONS ========================== */}
        <Route path="/reunions" element={<ReunionPage />} />
        <Route path="/reunions/ajouter" element={<ReunionForm />} />

        {/* ====================== RECHERCHE ========================= */}
        <Route path="/recherche" element={<SearchPage />} />

        {/* Default */}
        <Route path="*" element={<AccueilPage />} />
      </Routes>
    </>
  );
}

export default App;
