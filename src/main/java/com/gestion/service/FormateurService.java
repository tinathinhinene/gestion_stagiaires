package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.Formateur;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.FormateurRepository;

@Service
public class FormateurService {

    private final FormateurRepository repo;

    public FormateurService(FormateurRepository repo) {
        this.repo = repo;
    }

    // Récupérer l'utilisateur connecté
    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // -------------------------------------------------------------------------
    // GET ALL (Tous peuvent voir)
    // -------------------------------------------------------------------------
    public List<Formateur> getAll() {
        return repo.findAll();
    }

    // -------------------------------------------------------------------------
    // GET BY ID (Tous peuvent voir)
    // -------------------------------------------------------------------------
    public Formateur getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------
    public Formateur save(Formateur f) {
        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.save(f);
        }

        throw new RessourceAccessDeniedException("Accès refusé : seuls les admins peuvent créer un formateur");
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------
    public Formateur update(Integer id, Formateur f) {
        Utilisateur utilisateur = getCurrentUser();

        Formateur existant = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Formateur introuvable"));

        // ADMIN : peut tout modifier
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            f.setId(id);
            return repo.save(f);
        }

        // FORMATEUR : peut seulement modifier lui-même
        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            if (!utilisateur.getFormateur().getId().equals(id)) {
                throw new RessourceAccessDeniedException("Vous pouvez uniquement modifier votre propre profil");
            }

            f.setId(id);
            return repo.save(f);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------
    public void delete(Integer id) {
        Utilisateur utilisateur = getCurrentUser();

        Formateur f = repo.findById(id).orElse(null);

        if (f == null) return;

        // ADMIN : peut supprimer
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            repo.deleteById(id);
            return;
        }

        // FORMATEUR : ne peut supprimer personne, même pas lui-même
        throw new RessourceAccessDeniedException("Les formateurs ne peuvent supprimer aucun formateur");
    }
}
