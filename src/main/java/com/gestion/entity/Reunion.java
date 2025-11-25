package com.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reunion")
public class Reunion {
	
//======================================================================================
//attributs
//======================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reu_id")
    private Integer id;

    @Column(name = "reu_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "reu_duree")
    private Integer duree; // en minutes

    @Column(name = "reu_objet", length = 150)
    private String objet;

    @Column(name = "reu_description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "reu_type", length = 20)
    private TypeReunion type;

    @Column(name = "reu_actions", length =1000)
    private String actions;

    @Enumerated(EnumType.STRING)
    @Column(name = "reu_etat", length = 20)
    private EtatReunion etat;
    
    @ManyToOne
    @JoinColumn(name = "prj_id")
    private Projet projet;
    
    @ManyToOne
    @JoinColumn(name = "sta_id")
    private Stagiaire stagiaire ;
    
    @ManyToOne
    @JoinColumn(name = "for_id")
    private Formateur formateur;
//======================================================================================
  //constructeurs
//======================================================================================	
  	
    public Reunion() {}
   
  	public Reunion(Integer id, LocalDateTime date, Integer duree, String objet, String description, TypeReunion type,
		String actions, EtatReunion etat, Projet projet, Stagiaire stagiaire, Formateur formateur) {
	this.id = id;
	this.date = date;
	this.duree = duree;
	this.objet = objet;
	this.description = description;
	this.type = type;
	this.actions = actions;
	this.etat = etat;
	this.projet = projet;
	this.stagiaire = stagiaire;
	this.formateur = formateur;
}

	//======================================================================================
  	//getter setter
  	//======================================================================================

	  public Integer getId() {
		  return id;
	  }

	  public void setId(Integer id) {
		  this.id = id;
	  }

	  public LocalDateTime getDate() {
		  return date;
	  }

	  public void setDate(LocalDateTime date) {
		  this.date = date;
	  }

	  public Integer getDuree() {
		  return duree;
	  }

	  public void setDuree(Integer duree) {
		  this.duree = duree;
	  }

	  public String getObjet() {
		  return objet;
	  }

	  public void setObjet(String objet) {
		  this.objet = objet;
	  }

	  public String getDescription() {
		  return description;
	  }

	  public void setDescription(String description) {
		  this.description = description;
	  }

	  public TypeReunion getType() {
		  return type;
	  }

	  public void setType(TypeReunion type) {
		  this.type = type;
	  }

	  public String getActions() {
		  return actions;
	  }

	  public void setActions(String actions) {
		  this.actions = actions;
	  }

	  public EtatReunion getEtat() {
		  return etat;
	  }

	  public void setEtat(EtatReunion etat) {
		  this.etat = etat;
	  }
	  

	  public Projet getProjet() {
		return projet;
	}

	  public void setProjet(Projet projet) {
		  this.projet = projet;
	  }

	  public Stagiaire getStagiaire() {
		  return stagiaire;
	  }

	  public void setStagiaire(Stagiaire stagiaire) {
		  this.stagiaire = stagiaire;
	  }

	  public Formateur getFormateur() {
		  return formateur;
	  }

	  public void setFormateur(Formateur formateur) {
		  this.formateur = formateur;
	  }

	  @Override
	  public String toString() {
		return "Reunion [id=" + id + ", date=" + date + ", duree=" + duree + ", objet=" + objet + ", description="
				+ description + ", type=" + type + ", actions=" + actions + ", etat=" + etat + ", projet=" + projet
				+ ", stagiaire=" + stagiaire + ", formateur=" + formateur + "]";
	  }  
	  }