package com.gestion.repository;

import com.gestion.entity.Reunion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReunionRepository extends JpaRepository<Reunion, Integer> {
    List<Reunion> findByFormateurId(Integer formateurId);
}
