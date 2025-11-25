package com.gestion.security.dto;

public class AuthRequest {

    private String nom;          // nom en français
    private String motDePasse;   // mot de passe en français

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }
}
