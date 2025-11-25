package com.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Projet;

public interface ProjetRepository extends JpaRepository<Projet, Integer> {


    List<Projet> findByStage_Stagiaire_Formateur_Id(Integer formateurId);
}
