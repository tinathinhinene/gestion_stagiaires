package com.gestion.repository;

import com.gestion.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageRepository extends JpaRepository<Stage, Integer> {

    // ✔ Récupérer tous les stages dont le stagiaire appartient à un formateur
    List<Stage> findByStagiaire_Formateur_Id(Integer formateurId);

    // ✔ Supprimer un stage SEULEMENT si le stagiaire du stage appartient au formateur connecté
    int deleteByIdAndStagiaire_Formateur_Id(Integer id, Integer formateurId);

}
