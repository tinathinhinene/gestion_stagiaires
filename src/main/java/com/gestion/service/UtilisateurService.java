package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repo;

    public UtilisateurService(UtilisateurRepository repo) {
        this.repo = repo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ======================================================
    // GET ALL (ADMIN SEULEMENT)
    // ======================================================
    public List<Utilisateur> getAll() {

        Utilisateur user = getCurrentUser();

        if (user.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        throw new RessourceAccessDeniedException("Vous ne pouvez pas voir tous les utilisateurs");
    }

    // ======================================================
    // GET BY ID
    // ======================================================
    public Utilisateur getById(Integer id) {

        Utilisateur user = getCurrentUser();

        Utilisateur cible = repo.findById(id).orElse(null);
        if (cible == null) return null;

        // ADMIN → peut voir tout le monde
        if (user.getRole() == RoleUtilisateur.admin) {
            return cible;
        }

        // FORMATEUR → peut voir uniquement lui-même
        if (user.getId().equals(id)) {
            return cible;
        }

        throw new RessourceAccessDeniedException("Vous ne pouvez accéder qu'à votre propre compte");
    }

    // ======================================================
    // CREATE (ADMIN seulement)
    // ======================================================
    public Utilisateur create(Utilisateur u) {

        Utilisateur user = getCurrentUser();

        if (user.getRole() == RoleUtilisateur.admin) {
            return repo.save(u);
        }

        throw new RessourceAccessDeniedException("Vous ne pouvez pas créer un utilisateur");
    }

    // ======================================================
    // UPDATE
    // ======================================================
    public Utilisateur update(Integer id, Utilisateur u) {

        Utilisateur user = getCurrentUser();

        // ADMIN → peut tout modifier
        if (user.getRole() == RoleUtilisateur.admin) {
            u.setId(id);
            return repo.save(u);
        }

        // FORMATEUR → peut modifier uniquement son propre compte
        if (user.getId().equals(id)) {
            u.setId(id);
            u.setRole(user.getRole()); // Sécurité : il NE peut pas changer de rôle
            return repo.save(u);
        }

        throw new RessourceAccessDeniedException("Vous ne pouvez modifier que votre propre compte");
    }

    // ======================================================
    // DELETE
    // ======================================================
    public void delete(Integer id) {

        Utilisateur user = getCurrentUser();

        // ADMIN → OK
        if (user.getRole() == RoleUtilisateur.admin) {
            repo.deleteById(id);
            return;
        }

        throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer un utilisateur");
    }
}
