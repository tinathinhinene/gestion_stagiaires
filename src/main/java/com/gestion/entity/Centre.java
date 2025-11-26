package com.gestion.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "centre")
public class Centre {

//======================================================================================
    //attributs
//======================================================================================	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "cen_id")
private Integer id;

@Column(name = "cen_nom", nullable = false, length = 150)
private String nom;

@Column(name = "cen_adresse", nullable = false, length = 250)
private String adresse;

@Column(name = "cen_ville", length = 100)
private String ville;

@Column(name = "cen_code_postal", length = 10)
private String codePostal;

@Column(name = "cen_image", length = 150)
private String image;

@JsonIgnore
@OneToMany(mappedBy = "centre")
private List<Classe> classes;

//=================================================================
                      //constructeur
//=================================================================

public Centre() {
}
public Centre(Integer id, String nom, String adresse, String ville, String codePostal, String image) {
	this.id = id;
	this.nom = nom;
	this.adresse = adresse;
	this.ville = ville;
	this.codePostal = codePostal;
	this.image = image;
}
//=================================================================
//getter setter
//=================================================================
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
public String getAdresse() {
	return adresse;
}
public void setAdresse(String adresse) {
	this.adresse = adresse;
}
public String getVille() {
	return ville;
}
public void setVille(String ville) {
	this.ville = ville;
}
public String getCodePostal() {
	return codePostal;
}
public void setCodePostal(String codePostal) {
	this.codePostal = codePostal;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public List<Classe> getClasses() {
	return classes;
}
public void setClasses(List<Classe> classes) {
	this.classes = classes;
}

@Override
public String toString() {
	return "Centre [id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", ville=" + ville + ", codePostal="
			+ codePostal + ", image=" + image + "]";
}



}
