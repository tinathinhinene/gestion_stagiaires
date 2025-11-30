package com.gestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.entity.Formateur;

public interface FormateurRepository extends JpaRepository<Formateur, Integer> {
	 List<Formateur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
