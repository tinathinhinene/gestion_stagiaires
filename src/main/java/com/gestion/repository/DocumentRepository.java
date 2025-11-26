package com.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {

    // Documents appartenant aux stagiaires d’un formateur
    List<Document> findByStage_Stagiaire_Formateur_Id(Integer formateurId);
}
