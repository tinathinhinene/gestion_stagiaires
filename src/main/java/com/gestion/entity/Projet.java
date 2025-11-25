package com.gestion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "projet")
public class Projet {

//======================================================================================
    //attributs
//======================================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prj_id")
    private Integer id;

    @Column(name = "prj_titre", nullable = false, length = 150)
    private String titre;

    @Column(name = "prj_description", length = 500)
    private String description;

    @Column(name = "prj_avancement")
    private Integer avancement; // 0 à 100 %

    @Column(name = "prj_note", precision = 4, scale = 2)
    private BigDecimal note; // décimal 4,2

    @OneToOne
    @JoinColumn(name = "stage_id")
    @JsonManagedReference
    private Stage stage;

    
    @JsonIgnore
    @OneToMany(mappedBy = "projet")
    private List<Reunion> reunions;
    
    @JsonIgnore
    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Commentaire> commentaires;
    
  //======================================================================================
  //constructeurs
  //======================================================================================	

    public Projet() {}

	public Projet(Integer id, String titre, String description, Integer avancement, BigDecimal note, Stage stage) {
	this.id = id;
	this.titre = titre;
	this.description = description;
	this.avancement = avancement;
	this.note = note;
	this.stage = stage;
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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getAvancement() {
		return avancement;
	}

	public void setAvancement(Integer avancement) {
		this.avancement = avancement;
	}

	public BigDecimal getNote() {
		return note;
	}

	public void setNote(BigDecimal note) {
		this.note = note;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public List<Reunion> getReunions() {
		return reunions;
	}

	public void setReunions(List<Reunion> reunions) {
		this.reunions = reunions;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	@Override
	public String toString() {
		return "Projet [id=" + id + ", titre=" + titre + ", description=" + description + ", avancement=" + avancement
				+ ", note=" + note + ", stage=" + stage + "]";
	}
	}