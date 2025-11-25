package com.gestion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; //on importe jpa repository que spring fourni prété contient des methode: findall findbyid save deletebyid 
import com.gestion.entity.Stagiaire; //jimporte ma classe de entity

// public interface  = contrat , pas de code ici springboot fais une injection automatique , et jparepository interface de spring datajpa qui donne les methode crud direct et stagiaire est entité a gerer et integer type de clé primaire qui es id 
public interface StagiaireRepository extends JpaRepository<Stagiaire, Integer> {

	List<Stagiaire> findByFormateurId(Integer formateurId);//trouve tout les stagiaires dont l'id des formateur est ...
}
