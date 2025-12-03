// src/App.jsx
import { Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import LoginPage from "./pages/LoginPage";
import AccueilPage from "./pages/AccueilPage";

import StagiairesPage from "./pages/StagiairesPage";
import StagiaireForm from "./forms/StagiaireForm";
import StagiaireProfil from "./profils/StagiaireProfil";

import FormateursPage from "./pages/FormateursPage";
import FormateurForm from "./forms/FormateurForm";
import FormateurProfil from "./profils/FormateurProfil";

import ProjetForm from "./forms/ProjetForm";

import DocumentsPage from "./pages/DocumentsPage";
import DocumentForm from "./forms/DocumentForm";

import ReunionPage from "./pages/ReunionsPage";
import ReunionForm from "./forms/ReunionForm";

import SearchPage from "./pages/SearchPage";

function App() {
  return (
    <>
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
        <Route path="/formateurs/:id" element={<FormateurProfil />} />

        {/* Projets */}
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
    </>
  );
}

export default App;
