package com.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "commentaire")
public class Commentaire {
	
//======================================================================================
    //attributs
//======================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Integer id;

    @Column(name = "com_texte", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "com_date")
    private LocalDateTime date;
    
    @ManyToOne
    @JoinColumn(name = "for_id")
    private Formateur formateur;
    
    @ManyToOne
    @JoinColumn(name = "prj_id")
    private Projet projet;

//======================================================================================
    //constructeurs
//======================================================================================

    public Commentaire() {}   

	public Commentaire(Integer id, String message, LocalDateTime date, Formateur formateur, Projet projet) {
	super();
	this.id = id;
	this.message = message;
	this.date = date;
	this.formateur = formateur;
	this.projet = projet;
}

	@PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }


//======================================================================================
    //getters setters
//======================================================================================

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	@Override
	public String toString() {
		return "Commentaire [id=" + id + ", message=" + message + ", date=" + date + ", formateur=" + formateur
				+ ", projet=" + projet + "]";
	}
	
}
