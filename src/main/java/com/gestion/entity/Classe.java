package com.gestion.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "classe")
public class Classe {
	
//======================================================================================
    //attributs
//======================================================================================
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cla_id") 
    private Integer id;

    @Column(name = "cla_nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "cla_annee",length = 9)
    private String annee;

    @ManyToOne
	@JoinColumn(name = "cen_id")
	private Centre centre;
    
    @JsonIgnore
    @OneToMany(mappedBy = "classe")
    private List<Stagiaire> stagiaires;
//======================================================================================
    //constructeur
//======================================================================================
    public Classe() {
    }

    public Classe(Integer id, String nom, String annee, Centre centre) {
		this.id = id;
		this.nom = nom;
		this.annee = annee;
		this.centre = centre;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

	public Centre getCentre() {
		return centre;
	}

	public void setCentre(Centre centre) {
		this.centre = centre;
	}
	public List<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public void setStagiaires(List<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}

	@Override
	public String toString() {
		return "Classe [id=" + id + ", nom=" + nom + ", annee=" + annee + ", centre=" + centre + "]";
	}

}