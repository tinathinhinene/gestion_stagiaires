package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.Reunion;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Stagiaire;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.ReunionRepository;

@Service
public class ReunionService {

    private final ReunionRepository repo;

    public ReunionService(ReunionRepository repo) {
        this.repo = repo;
    }

    // Récupère l'utilisateur connecté
    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // LIRE TOUTES LES RÉUNIONS
    public List<Reunion> getAll() {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            return repo.findByFormateurId(utilisateur.getFormateur().getId());
        }

        return List.of();
    }

    // LIRE UNE RÉUNION PAR ID
    public Reunion getById(Integer id) {

        Utilisateur utilisateur = getCurrentUser();

        Reunion r = repo.findById(id).orElse(null);
        if (r == null) return null;

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return r;
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            if (r.getFormateur() != null &&
                r.getFormateur().getId().equals(utilisateur.getFormateur().getId())) {

                return r;
            }
            throw new RessourceAccessDeniedException("Vous n'avez pas accès à cette réunion");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // CRÉER / MODIFIER UNE RÉUNION
    public Reunion save(Reunion r) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.save(r);
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            // On force ce formateur comme propriétaire
            r.setFormateur(utilisateur.getFormateur());

            Reunion existante = null;
            if (r.getId() != null) {
                existante = repo.findById(r.getId()).orElse(null);
            }

            // Empêcher la modification d'une réunion d'un autre formateur
            if (existante != null &&
                existante.getFormateur() != null &&
                !existante.getFormateur().getId().equals(utilisateur.getFormateur().getId())) {

                throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier cette réunion");
            }

            return repo.save(r);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // SUPPRIMER
    public void delete(Integer id) {

        Utilisateur utilisateur = getCurrentUser();

        Reunion r = repo.findById(id).orElse(null);
        if (r == null) return;

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            repo.deleteById(id);
            return;
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            if (r.getFormateur() != null &&
                r.getFormateur().getId().equals(utilisateur.getFormateur().getId())) {

                repo.deleteById(id);
                return;
            }
            throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer cette réunion");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }


	public List<Reunion> saveAll(List<Reunion> reunions) {
	    Utilisateur utilisateur = getCurrentUser();

    if (utilisateur.getRole() == RoleUtilisateur.admin) {
        return repo.saveAll(reunions);
    }

    if (utilisateur.getRole() == RoleUtilisateur.formateur) {
        // On force le formateur pour CHAQUE reunion
        for (Reunion r : reunions) {
            r.setFormateur(utilisateur.getFormateur());
        }

        return repo.saveAll(reunions);
    }

    throw new RessourceAccessDeniedException("Accès refusé");
}
}













