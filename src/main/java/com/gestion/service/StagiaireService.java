
package com.gestion.service;

import java.util.List;//pour stocker plusieur objet plusieur stagiaires
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service; //c'est pour dire cette classe est un service

import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Stagiaire;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.StagiaireRepository; //interface de communication avec bdd

@Service //fais de cette classe  un composant metier qui contient logique metier
public class StagiaireService {
	
private final StagiaireRepository repo;

public StagiaireService(StagiaireRepository repo) { //injection par constructeur
		this.repo = repo; //attribut de classe = parametre du contructeur
}

// je recupere l'utilisateur actuel
private Utilisateur getCurrentUser() { //methode qui recupere utilisateur connécté celui qui a fait la requete avec son tokenjwt)
    Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //spring garde en memeoire les infos de l'utilisateur connécté
    return (Utilisateur) auth.getPrincipal(); // car Utilisateur implémente customUserDetails
}
//lire tous les stagiaire

public List<Stagiaire> getAll() { //renvoi liste stagiaire
	Utilisateur utilisateur = getCurrentUser();
	
	if(utilisateur.getRole() == RoleUtilisateur.admin) {
		//les admin voit tout les stagiaires 
	
	return repo.findAll();     //donne toute les ligne de  table
}
	  if (utilisateur.getRole() == RoleUtilisateur.formateur) {
          return repo.findByFormateurId(utilisateur.getFormateur().getId());
      }

      return List.of();
  }

// lire un seul 

public Stagiaire getById(Integer id) {
    Utilisateur utilisateur = getCurrentUser();

    Stagiaire s = repo.findById(id).orElse(null);
    if (s == null) return null;

    if (utilisateur.getRole() == RoleUtilisateur.admin) {
        return s;
    }

    if (utilisateur.getRole() == RoleUtilisateur.formateur) {
        if (s.getFormateur() != null &&
            s.getFormateur().getId().equals(utilisateur.getFormateur().getId())) {

            return s;
        }
        throw new RessourceAccessDeniedException("Vous n'avez pas accès à ce stagiaire");
    }

    throw new RessourceAccessDeniedException("Accès refusé");
}

// creer ou modifier un stagiaire

public Stagiaire save(Stagiaire s) {
Utilisateur utilisateur = getCurrentUser();

//admin peut tout faire
if (utilisateur.getRole() == RoleUtilisateur.admin) {
	return repo.save(s);
	}
if (utilisateur.getRole() == RoleUtilisateur.formateur) {

    s.setFormateur(utilisateur.getFormateur());

    Stagiaire existant = null;

    if (s.getId() != null) {
        existant = repo.findById(s.getId()).orElse(null);
    }

    if (existant != null &&
        existant.getFormateur() != null &&
        !existant.getFormateur().getId().equals(utilisateur.getFormateur().getId())) {

        throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier ce stagiaire");
    }

    return repo.save(s);
}

throw new RessourceAccessDeniedException("Accès refusé");
}
// supprimer
public void delete(Integer id) { 
	Utilisateur utilisateur = getCurrentUser();
	Stagiaire s = repo.findById(id).orElse(null);
	if(s == null) return;
	
	if (utilisateur.getRole() == RoleUtilisateur.admin) {
		repo.deleteById(id);
		return;
		}
	
	if (utilisateur.getRole() == RoleUtilisateur.formateur) {
		if (s.getFormateur() != null &&
	            s.getFormateur().getId().equals(utilisateur.getFormateur().getId())) {

	repo.deleteById(id);
	return;
	
}
		  throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer ce stagiaire");
    }
        throw new RessourceAccessDeniedException("Accès refusé");
    }
//save all
public List<Stagiaire> saveAll(List<Stagiaire> stagiaires) {
    Utilisateur utilisateur = getCurrentUser();

    if (utilisateur.getRole() == RoleUtilisateur.admin) {
        return repo.saveAll(stagiaires);
    }

    if (utilisateur.getRole() == RoleUtilisateur.formateur) {
        // On force le formateur pour CHAQUE stagiaire
        for (Stagiaire s : stagiaires) {
            s.setFormateur(utilisateur.getFormateur());
        }

        return repo.saveAll(stagiaires);
    }

    throw new RessourceAccessDeniedException("Accès refusé");
}
}


