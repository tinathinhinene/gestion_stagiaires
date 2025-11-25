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
        Utilisateur utilisateur = getCurrentUser();

        // ADMIN → voit tout
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        // FORMATEUR → ne voit que ses stages
        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            return repo.findByStagiaire_Formateur_Id(
                    utilisateur.getFormateur().getId()
            );
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // GET by ID
    // ============================================================
    public Stage getById(Integer id) {
        Utilisateur utilisateur = getCurrentUser();

        Stage stage = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

        // ADMIN → OK
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return stage;
        }

        // FORMATEUR → doit appartenir au formateur
        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            if (stage.getStagiaire() != null &&
                stage.getStagiaire().getFormateur() != null &&
                stage.getStagiaire().getFormateur().getId()
                        .equals(utilisateur.getFormateur().getId())) {

                return stage;
            }

            throw new RessourceAccessDeniedException("Vous n'avez pas accès à ce stage");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // CREATE
    // ============================================================
    public Stage save(Stage s) {
        Utilisateur utilisateur = getCurrentUser();

        // ADMIN → OK
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.save(s);
        }

        // FORMATEUR
        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            // Vérifier que le stagiaire appartient à ce formateur
            if (s.getStagiaire() == null ||
                s.getStagiaire().getFormateur() == null ||
                !s.getStagiaire().getFormateur().getId()
                        .equals(utilisateur.getFormateur().getId())) {

                throw new RessourceAccessDeniedException(
                        "Ce stagiaire n'appartient pas à ce formateur"
                );
            }

            return repo.save(s);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // UPDATE
    // ============================================================
    public Stage update(Integer id, Stage newStage) {
        Utilisateur utilisateur = getCurrentUser();

        Stage old = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

        // ADMIN → OK
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            newStage.setId(id);
            return repo.save(newStage);
        }

        // FORMATEUR
        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            // Vérifier qu'il s'agit de SON stage
            if (old.getStagiaire().getFormateur().getId()
                    .equals(utilisateur.getFormateur().getId())) {

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
     Utilisateur utilisateur = getCurrentUser();

     Stage stage = repo.findById(id)
             .orElse(null);

     if (stage == null) return;

     // ADMIN → peut supprimer tout
     if (utilisateur.getRole() == RoleUtilisateur.admin) {
         repo.deleteById(id);
         return;
     }

     // FORMATEUR → peut supprimer uniquement ses stages
     if (utilisateur.getRole() == RoleUtilisateur.formateur) {

         // Important : vérifier que le stagiaire appartient au formateur connecté
         if (stage.getStagiaire() != null &&
             stage.getStagiaire().getFormateur() != null &&
             stage.getStagiaire().getFormateur().getId()
                 .equals(utilisateur.getFormateur().getId())) {

             repo.deleteById(id);
             return;
         }

         throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer ce stage");
     }

     // AUTRES ROLES → interdit
     throw new RessourceAccessDeniedException("Accès refusé");
 }
}