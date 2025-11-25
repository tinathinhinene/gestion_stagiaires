package com.gestion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

	Optional<Utilisateur> findByNom(String nom);

}
