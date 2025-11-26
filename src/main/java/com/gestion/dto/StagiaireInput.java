package com.gestion.dto;

import java.time.LocalDate;

public class StagiaireInput {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private LocalDate dateNaiss;
    private Boolean actif;

    private MiniObject formateur;
    private MiniObject classe;

    public static class MiniObject {
        private Integer id;
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
    }

    // --- getters & setters ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }

    public LocalDate getDateNaiss() { return dateNaiss; }
    public void setDateNaiss(LocalDate dateNaiss) { this.dateNaiss = dateNaiss; }

    public Boolean getActif() { return actif; }
    public void setActif(Boolean actif) { this.actif = actif; }

    public MiniObject getFormateur() { return formateur; }
    public void setFormateur(MiniObject formateur) { this.formateur = formateur; }

    public MiniObject getClasse() { return classe; }
    public void setClasse(MiniObject classe) { this.classe = classe; }
}
