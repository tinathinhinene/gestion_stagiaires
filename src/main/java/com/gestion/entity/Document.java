package com.gestion.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "document")
public class Document {
//	======================================================================================
            //attributs
//======================================================================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_id")
    private Integer id;

    @Column(name = "doc_nom", nullable = false, length = 150)
    private String nom;

    @Column(name = "doc_type", nullable = false, length = 50)
    private String type;
    
    @Column(name = "doc_chemin", nullable = false, length = 255)
    private String chemin;

    @Column(name = "doc_date_upload")
    private LocalDateTime date;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "stg_id")
    private Stage stage;
    
  //======================================================================================
  //constructeurs
  //======================================================================================	  
	public Document() {
			}

    public Document(Integer id, String nom, String type, String chemin, LocalDateTime date, Stage stage) {
	this.id = id;
	this.nom = nom;
	this.type = type;
	this.chemin = chemin;
	this.date = date;
	this.stage = stage;
}



	// =====================================================
    // Initialisation automatique de la date
    // =====================================================
    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", nom=" + nom + ", type=" + type + ", chemin=" + chemin + ", date=" + date
				+ ", stage=" + stage + "]";
	}
    
}

   