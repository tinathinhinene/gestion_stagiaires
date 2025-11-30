package com.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Stagiaire;

public interface StagiaireRepository extends JpaRepository<Stagiaire, Integer> {

    List<Stagiaire> findByFormateur_Id(Integer formateurId);
    List<Stagiaire> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
    
}
