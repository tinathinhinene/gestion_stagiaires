package com.gestion.dto;

public class DocumentRequest {

    private String nom;
    private String type;
    private String chemin;

    private StageIdDTO stage;  // stage : { id: X }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getChemin() { return chemin; }
    public void setChemin(String chemin) { this.chemin = chemin; }

    public StageIdDTO getStage() { return stage; }
    public void setStage(StageIdDTO stage) { this.stage = stage; }

    // Sous-DTO pour : "stage": {"id":46}
    public static class StageIdDTO {
        private Integer id;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
    }
}
