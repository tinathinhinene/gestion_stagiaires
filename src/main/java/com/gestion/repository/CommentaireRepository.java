package com.gestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Commentaire;

public interface CommentaireRepository extends JpaRepository<Commentaire, Integer> {
}
