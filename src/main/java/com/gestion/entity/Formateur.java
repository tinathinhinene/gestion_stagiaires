package com.gestion.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "formateur")
public class Formateur {

	//======================================================================================
		                      //attributs
	//======================================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "for_id")
    private Integer id;

    @Column(name = "for_nom", length = 100, nullable = false)
    private String nom;

    @Column(name = "for_prenom", length = 100, nullable = false)
    private String prenom;

    @Column(name = "for_Email", length = 150, nullable = false, unique = true)
    private String email;

    @Column(name = "for_tel", length = 20)
    private String tel;

    @Column(name = "for_specialite", length = 100)
    private String specialite;

    @Column(name = "for_image", length = 150)
    private String image;
    
    @JsonIgnore
    @OneToMany(mappedBy = "formateur")
    private List<Reunion> reunions;
    
    @JsonIgnore
    @OneToMany(mappedBy = "formateur")
    private List<Stagiaire> stagiaires;
    
    @JsonIgnore
    @OneToMany(mappedBy = "formateur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "uti_id")
    private Utilisateur utilisateur;
    
    //======================================================================================
  //constructeurs
  //======================================================================================	
  	
    public Formateur() {}

    	public Formateur(Integer id, String nom, String prenom, String email, String tel, String specialite,
			String image, List<Reunion> reunions, List<Stagiaire> stagiaires,
			List<Commentaire> commentaires, Utilisateur utilisateur) {
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.tel = tel;
		this.specialite = specialite;
		this.image = image;
		this.utilisateur = utilisateur;
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

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Reunion> getReunions() {
		return reunions;
	}

	public void setReunions(List<Reunion> reunions) {
		this.reunions = reunions;
	}

	public List<Stagiaire> getStagiaires() {
		return stagiaires;
	}

	public void setStagiaires(List<Stagiaire> stagiaires) {
		this.stagiaires = stagiaires;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public String toString() {
		return "Formateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", tel=" + tel
				+ ", specialite=" + specialite + ", image=" + image
				+ ", utilisateur=" + utilisateur + "]";
	}

	}
