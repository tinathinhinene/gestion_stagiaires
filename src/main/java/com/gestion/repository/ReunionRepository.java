package com.gestion.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.entity.Reunion;

public interface ReunionRepository extends JpaRepository<Reunion, Integer> {

    List<Reunion> findByStagiaire_Formateur_Id(Integer formateurId);
}
