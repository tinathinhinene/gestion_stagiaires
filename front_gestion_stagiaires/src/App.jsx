import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import LoginPage from "./pages/LoginPage";
import AccueilPage from "./pages/AccueilPage";

import StagiairesPage from "./pages/StagiairesPage";
import StagiaireForm from "./pages/StagiaireForm";
import StagiaireProfil from "./pages/StagiaireProfil";

import FormateursPage from "./pages/FormateursPage";
import FormateurForm from "./pages/FormateurForm";

import StagesPage from "./pages/StagesPage";
import StageForm from "./pages/StageForm";

import ProjetsPage from "./pages/ProjetsPage";
import ProjetForm from "./pages/ProjetForm";

import DocumentsPage from "./pages/DocumentsPage";
import DocumentForm from "./pages/DocumentForm";

import ReunionPage from "./pages/ReunionPage";
import ReunionForm from "./pages/ReunionForm";

import SearchPage from "./pages/SearchPage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        {/* Auth */}
        <Route path="/login" element={<LoginPage />} />

        {/* Accueil */}
        <Route path="/accueil" element={<AccueilPage />} />

        {/* Stagiaires */}
        <Route path="/stagiaires" element={<StagiairesPage />} />
        <Route path="/stagiaires/ajouter" element={<StagiaireForm />} />
        <Route path="/stagiaires/modifier/:id" element={<StagiaireForm />} />
        <Route path="/stagiaires/:id" element={<StagiaireProfil />} />

        {/* Formateurs */}
        <Route path="/formateurs" element={<FormateursPage />} />
        <Route path="/formateurs/ajouter" element={<FormateurForm />} />
        <Route path="/formateurs/modifier/:id" element={<FormateurForm />} />

        {/* Stages */}
        <Route path="/stages" element={<StagesPage />} />
        <Route path="/stages/ajouter" element={<StageForm />} />
        <Route path="/stages/modifier/:id" element={<StageForm />} />

        {/* Projets */}
        <Route path="/projets" element={<ProjetsPage />} />
        <Route path="/projets/ajouter" element={<ProjetForm />} />
        <Route path="/projets/modifier/:id" element={<ProjetForm />} />

        {/* Documents */}
        <Route path="/documents" element={<DocumentsPage />} />
        <Route path="/documents/ajouter" element={<DocumentForm />} />

        {/* Réunions */}
        <Route path="/reunions" element={<ReunionPage />} />
        <Route path="/reunions/ajouter" element={<ReunionForm />} />

        {/* Recherche */}
        <Route path="/recherche" element={<SearchPage />} />

        {/* Default */}
        <Route path="*" element={<AccueilPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
