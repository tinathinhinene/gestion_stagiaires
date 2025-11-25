package com.gestion.repository;

import com.gestion.entity.Stage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StageRepository extends JpaRepository<Stage, Integer> {
	List<Stage> findByStagiaire_Formateur_Id(Integer formateurId);

}

