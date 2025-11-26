package com.gestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.entity.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {

    // Récupérer tous les commentaires d'un formateur
    List<Commentaire> findByFormateur_Id(Integer formateurId);
}
