package com.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stage")
public class Stage {
	
//======================================================================================
//attributs
//======================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stg_id")
    private Integer id;

    @Column(name = "stg_date_debut", nullable = false)
    private LocalDate dateDebut;

    @Column(name = "stg_date_fin", nullable = false)
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "stg_etat", length = 20)
    private EtatStage etat;

    @Column(name = "stg_convention_signee")
    private Boolean conventionSignee = false;

    @Column(name = "stg_attestation_livree")
    private Boolean attestationLivree = false;

    @OneToOne(mappedBy = "stage")
    @JsonBackReference
    private Projet projet;
    
    @ManyToOne
    @JoinColumn(name = "sta_id")
    private Stagiaire stagiaire;
    
    @JsonIgnore  
    @OneToMany(mappedBy = "stage", cascade = CascadeType.ALL, orphanRemoval = true) //cascade =... si je supprime un stage je supprime ses doc, et orphanremoval =  true , si un document n 'est plus ataché a un stage supprime automatiquement
       private List<Document> documents;
    
     //======================================================================================
  //constructeurs
  //======================================================================================
    
    public Stage() {} 

	public Stage(Integer id, LocalDate dateDebut, LocalDate dateFin, EtatStage etat, Boolean conventionSignee,
		Boolean attestationLivree, Projet projet, Stagiaire stagiaire) {
	super();
	this.id = id;
	this.dateDebut = dateDebut;
	this.dateFin = dateFin;
	this.etat = etat;
	this.conventionSignee = conventionSignee;
	this.attestationLivree = attestationLivree;
	this.projet = projet;
	this.stagiaire = stagiaire;
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

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public EtatStage getEtat() {
		return etat;
	}

	public void setEtat(EtatStage etat) {
		this.etat = etat;
	}

	public Boolean getConventionSignee() {
		return conventionSignee;
	}

	public void setConventionSignee(Boolean conventionSignee) {
		this.conventionSignee = conventionSignee;
	}

	public Boolean getAttestationLivree() {
		return attestationLivree;
	}

	public void setAttestationLivree(Boolean attestationLivree) {
		this.attestationLivree = attestationLivree;
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

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	@Override
	public String toString() {
		return "Stage [id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", etat=" + etat
				+ ", conventionSignee=" + conventionSignee + ", attestationLivree=" + attestationLivree + ", projet="
				+ projet + ", stagiaire=" + stagiaire + "]";
	}

	

}