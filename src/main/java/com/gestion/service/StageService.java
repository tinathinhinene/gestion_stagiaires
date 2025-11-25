package com.gestion.service;

import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Stage;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.StageRepository;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StageService {

    private final StageRepository repo;

    public StageService(StageRepository repo) {
        this.repo = repo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ============================================================
    // GET ALL
    // ============================================================
    public List<Stage> getAll() {
        Utilisateur u = getCurrentUser();

        if (u.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            Integer formateurId = u.getFormateur().getId();
            return repo.findByStagiaire_Formateur_Id(formateurId);  // ✔ correct
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // GET by ID
    // ============================================================
    public Stage getById(Integer id) {
        Utilisateur u = getCurrentUser();

        Stage stage = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

        if (u.getRole() == RoleUtilisateur.admin) {
            return stage;
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            Integer formateurId = u.getFormateur().getId();

            if (stage.getStagiaire() != null &&
                stage.getStagiaire().getFormateur() != null &&
                stage.getStagiaire().getFormateur().getId().equals(formateurId)) {

                return stage;
            }

            throw new RessourceAccessDeniedException("Accès refusé à ce stage");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // CREATE
    // ============================================================
    public Stage save(Stage s) {
        Utilisateur u = getCurrentUser();

        if (u.getRole() == RoleUtilisateur.admin) {
            return repo.save(s);
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            Integer formateurId = u.getFormateur().getId();

            if (s.getStagiaire() == null ||
                s.getStagiaire().getFormateur() == null ||
                !s.getStagiaire().getFormateur().getId().equals(formateurId)) {

                throw new RessourceAccessDeniedException("Ce stagiaire ne vous appartient pas");
            }

            return repo.save(s);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // UPDATE
    // ============================================================
    public Stage update(Integer id, Stage newStage) {
        Utilisateur u = getCurrentUser();

        Stage old = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

        if (u.getRole() == RoleUtilisateur.admin) {
            newStage.setId(id);
            return repo.save(newStage);
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            Integer formateurId = u.getFormateur().getId();

            if (old.getStagiaire().getFormateur().getId().equals(formateurId)) {
                newStage.setId(id);
                return repo.save(newStage);
            }

            throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier ce stage");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // DELETE
    // ============================================================
    public void delete(Integer id) {
        Utilisateur u = getCurrentUser();

        Stage stage = repo.findById(id).orElse(null);
        if (stage == null) return;

        if (u.getRole() == RoleUtilisateur.admin) {
            repo.deleteById(id);
            return;
        }

        if (u.getRole() == RoleUtilisateur.formateur) {

            Integer formateurId = u.getFormateur().getId();

            boolean autorise =
                    stage.getStagiaire() != null &&
                    stage.getStagiaire().getFormateur() != null &&
                    stage.getStagiaire().getFormateur().getId().equals(formateurId);

            if (autorise) {
                repo.deleteById(id);
                return;
            }

            throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer ce stage");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }
}
