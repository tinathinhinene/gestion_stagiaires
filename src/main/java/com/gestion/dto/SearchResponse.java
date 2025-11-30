package com.gestion.dto;

import java.util.List;

import com.gestion.entity.Stagiaire;
import com.gestion.entity.Formateur;
import com.gestion.entity.Projet;

public class SearchResponse {

    private String query;
    private List<Stagiaire> stagiaires;
    private List<Formateur> formateurs;
    private List<Projet> projets;

    public SearchResponse() {}

    public SearchResponse(
            String query,
            List<Stagiaire> stagiaires,
            List<Formateur> formateurs,
            List<Projet> projets
    ) {
        this.query = query;
        this.stagiaires = stagiaires;
        this.formateurs = formateurs;
        this.projets = projets;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Stagiaire> getStagiaires() {
        return stagiaires;
    }

    public void setStagiaires(List<Stagiaire> stagiaires) {
        this.stagiaires = stagiaires;
    }

    public List<Formateur> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(List<Formateur> formateurs) {
        this.formateurs = formateurs;
    }

    public List<Projet> getProjets() {
        return projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }
}
