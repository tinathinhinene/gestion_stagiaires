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
import com.gestion.repository.StagiaireRepository;

@Service
public class ReunionService {

    private final ReunionRepository repo;
    private final StagiaireRepository stagiaireRepo;

    public ReunionService(ReunionRepository repo, StagiaireRepository stagiaireRepo) {
        this.repo = repo;
        this.stagiaireRepo = stagiaireRepo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ============================================================
    // GET ALL
    // ============================================================
    public List<Reunion> getAll() {
        Utilisateur u = getCurrentUser();

        if (u.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            return repo.findByStagiaire_Formateur_Id(u.getFormateur().getId());
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    public Reunion getById(Integer id) {
        Utilisateur u = getCurrentUser();

        Reunion r = repo.findById(id).orElse(null);
        if (r == null) return null;

        if (u.getRole() == RoleUtilisateur.admin) return r;

        if (u.getRole() == RoleUtilisateur.formateur &&
            r.getStagiaire().getFormateur().getId().equals(u.getFormateur().getId())) {
            return r;
        }

        throw new RessourceAccessDeniedException("Accès interdit");
    }

    // ============================================================
    // SAVE (création ou modification, comme pour Projet)
    // ============================================================
    public Reunion save(Reunion r) {

        if (r.getId() == null) {
            // création
            return create(r);
        } else {
            // modification
            return update(r.getId(), r);
        }
    }

    // ============================================================
    // CREATE
    // ============================================================
    public Reunion create(Reunion r) {

        Utilisateur u = getCurrentUser();

        if (r.getStagiaire() == null || r.getStagiaire().getId() == null) {
            throw new RessourceAccessDeniedException("Stagiaire obligatoire");
        }

        Stagiaire stg = stagiaireRepo.findById(r.getStagiaire().getId())
                    .orElseThrow(() -> new RessourceAccessDeniedException("Stagiaire introuvable"));

        if (u.getRole() == RoleUtilisateur.formateur &&
            !stg.getFormateur().getId().equals(u.getFormateur().getId())) {

            throw new RessourceAccessDeniedException("Vous ne pouvez créer une réunion que pour vos stagiaires");
        }

        r.setStagiaire(stg);
        r.setFormateur(stg.getFormateur()); // cohérence
        // projet : tu peux le laisser tel quel, tu l'as déjà mis dans le formulaire

        return repo.save(r);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    public Reunion update(Integer id, Reunion r) {

        Utilisateur u = getCurrentUser();

        Reunion existante = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Réunion introuvable"));

        if (u.getRole() == RoleUtilisateur.formateur &&
            !existante.getStagiaire().getFormateur().getId()
                .equals(u.getFormateur().getId())) {
            throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier cette réunion");
        }

        existante.setDate(r.getDate());
        existante.setDuree(r.getDuree());
        existante.setObjet(r.getObjet());
        existante.setDescription(r.getDescription());
        existante.setType(r.getType());
        existante.setActions(r.getActions());
        existante.setEtat(r.getEtat());
        existante.setProjet(r.getProjet()); // si tu veux pouvoir changer le projet aussi

        return repo.save(existante);
    }

    // ============================================================
    // DELETE
    // ============================================================
    public void delete(Integer id) {

        Utilisateur u = getCurrentUser();

        Reunion r = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Réunion introuvable"));

        if (u.getRole() == RoleUtilisateur.admin ||
           (u.getRole() == RoleUtilisateur.formateur &&
            r.getStagiaire().getFormateur().getId().equals(u.getFormateur().getId()))) {

            repo.deleteById(id);
            return;
        }

        throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer cette réunion");
    }
}
