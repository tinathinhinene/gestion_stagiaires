package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.Centre;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.CentreRepository;

@Service
public class CentreService {

    private final CentreRepository repo;

    public CentreService(CentreRepository repo) {
        this.repo = repo;
    }

    // Récupère l’utilisateur connecté
    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // =========================================
    // GET ALL
    // =========================================
    public List<Centre> getAll() {
        return repo.findAll();  // ADMIN + FORMATEUR autorisés
    }

    // =========================================
    // GET BY ID
    // =========================================
    public Centre getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // =========================================
    // CREATE (ADMIN + FORMATEUR)
    // =========================================
    public Centre create(Centre centre) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin ||
            utilisateur.getRole() == RoleUtilisateur.formateur) {

            return repo.save(centre);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // =========================================
    // UPDATE (ADMIN seul)
    // =========================================
    public Centre update(Integer id, Centre centre) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() != RoleUtilisateur.admin) {
            throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier un centre");
        }

        centre.setId(id);
        return repo.save(centre);
    }

    // =========================================
    // DELETE (ADMIN seul)
    // =========================================
    public void delete(Integer id) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() != RoleUtilisateur.admin) {
            throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer un centre");
        }

        repo.deleteById(id);
    }
}
