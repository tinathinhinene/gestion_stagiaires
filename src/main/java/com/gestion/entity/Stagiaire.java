package com.gestion.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*; //pour utiliser les annotatoions jpa telque @entity @table @id 

@Entity  // ecoute spring /jpa cette classe c'est une table dans ma bdd
@Table(name = "stagiaire") 
public class Stagiaire {
	
//======================================================================================
	                      //attributs
//======================================================================================
	
	@Id      //clé primaire
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // bdd génére mon id toute seule
	@Column(name = "sta_id")  //nom de la colonne de ma bdd
	private Integer id;     //dans java je c'est id
	
	@Column(name = "sta_nom", nullable = false)  //colonne de bdd
	private String nom;
	
	@Column(name = "sta_prenom", nullable = false)
	private String prenom;

	@Column(name = "sta_email", nullable = false, unique = true)
	private String email;
	
	@Column(name = "sta_tel")
	private String tel;
	
	@Column(name = "sta_date_naiss")
	private LocalDate dateNaiss;
	
	@Column(name = "sta_actif")
	private Boolean actif;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "for_id")
	private Formateur formateur;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "cla_id")
	private Classe classe;

	@JsonIgnore
	@OneToMany(mappedBy = "stagiaire")
	private List<Reunion> reunions;
//======================================================================================
//constructeurs
//======================================================================================	
	
	public Stagiaire() {} //constructeur vide

	public Stagiaire(Integer id, String nom, String prenom, String email, String tel, LocalDate dateNaiss,
			Boolean actif, Formateur formateur, Classe classe) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
		this.dateNaiss = dateNaiss;
		this.actif = actif;
		this.formateur = formateur;
		this.classe = classe;
	}

	//======================================================================================
	//getter setter
	//======================================================================================	
		// sa permet a spring/jpa de remplir mon objet et au controller /service d'acceder a mes valeur	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public LocalDate getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(LocalDate dateNaiss) {
		this.dateNaiss = dateNaiss;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	
	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Reunion> getReunions() {
		return reunions;
	}

	public void setReunions(List<Reunion> reunions) {
		this.reunions = reunions;
	}

	@Override
	public String toString() {
		return "Stagiaire [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", tel=" + tel
				+ ", dateNaiss=" + dateNaiss + ", actif=" + actif + ", formateur=" + formateur + ", classe=" + classe
				+ "]";
	}

	


	
	
	
}
