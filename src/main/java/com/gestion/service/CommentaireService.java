package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.Commentaire;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Utilisateur;
import com.gestion.entity.Projet;
import com.gestion.repository.CommentaireRepository;
import com.gestion.repository.ProjetRepository;
import com.gestion.exception.RessourceAccessDeniedException;

@Service
public class CommentaireService {

    private final CommentaireRepository repo;
    private final ProjetRepository projetRepo;

    public CommentaireService(CommentaireRepository repo, ProjetRepository projetRepo) {
        this.repo = repo;
        this.projetRepo = projetRepo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ==========================================================
    // GET ALL
    // ==========================================================
    public List<Commentaire> getAll() {
        Utilisateur user = getCurrentUser();

        if (user.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (user.getRole() == RoleUtilisateur.formateur) {
            return repo.findByFormateur_Id(user.getFormateur().getId());
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ==========================================================
    // GET BY ID
    // ==========================================================
    public Commentaire getById(Integer id) {
        Utilisateur user = getCurrentUser();

        Commentaire com = repo.findById(id).orElse(null);
        if (com == null) return null;

        if (user.getRole() == RoleUtilisateur.admin) {
            return com;
        }

        if (user.getRole() == RoleUtilisateur.formateur) {
            if (com.getFormateur().getId().equals(user.getFormateur().getId())) {
                return com;
            }
            throw new RessourceAccessDeniedException("Vous n'avez pas accès à ce commentaire");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ==========================================================
    // CREATE
    // ==========================================================
    public Commentaire create(Commentaire c) {

        Utilisateur user = getCurrentUser();

        // ADMIN → OK
        if (user.getRole() == RoleUtilisateur.admin) {
            return repo.save(c);
        }

        // FORMATEUR →
        if (user.getRole() == RoleUtilisateur.formateur) {

            if (c.getProjet() == null || c.getProjet().getId() == null)
                throw new RessourceAccessDeniedException("Projet obligatoire");

            Projet projet = projetRepo.findById(c.getProjet().getId())
                    .orElseThrow(() -> new RessourceAccessDeniedException("Projet introuvable"));

            if (!projet.getStage().getStagiaire().getFormateur().getId()
                    .equals(user.getFormateur().getId())) {
                throw new RessourceAccessDeniedException("Le projet appartient à un autre formateur");
            }

            // Associer automatiquement le formateur connecté
            c.setFormateur(user.getFormateur());

            return repo.save(c);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ==========================================================
    // UPDATE
    // ==========================================================
    public Commentaire update(Integer id, Commentaire c) {

        Utilisateur user = getCurrentUser();
        Commentaire existant = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Commentaire introuvable"));

        // ADMIN → OK
        if (user.getRole() == RoleUtilisateur.admin) {
            c.setId(id);
            return repo.save(c);
        }

        // FORMATEUR → OK uniquement si c'est lui
        if (user.getRole() == RoleUtilisateur.formateur) {

            if (!existant.getFormateur().getId().equals(user.getFormateur().getId())) {
                throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier ce commentaire");
            }

            c.setId(id);
            c.setFormateur(user.getFormateur()); // sécurité
            return repo.save(c);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ==========================================================
    // DELETE
    // ==========================================================
    public void delete(Integer id) {

        Utilisateur user = getCurrentUser();
        Commentaire com = repo.findById(id).orElse(null);

        if (com == null) return;

        // ADMIN → OK
        if (user.getRole() == RoleUtilisateur.admin) {
            repo.deleteById(id);
            return;
        }

        // FORMATEUR → uniquement ses commentaires
        if (user.getRole() == RoleUtilisateur.formateur) {

            if (!com.getFormateur().getId().equals(user.getFormateur().getId())) {
                throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer ce commentaire");
            }

            repo.deleteById(id);
            return;
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }
}
