package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.Projet;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Stage;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.ProjetRepository;
import com.gestion.repository.StageRepository;

@Service
public class ProjetService {

    private final ProjetRepository repo;
    private final StageRepository stageRepo;  // 👈 AJOUT

    // 👇 AJOUT du StageRepository dans le constructeur
    public ProjetService(ProjetRepository repo, StageRepository stageRepo) {
        this.repo = repo;
        this.stageRepo = stageRepo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    public List<Projet> getAll() {
        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            return repo.findByStage_Stagiaire_Formateur_Id(
                    utilisateur.getFormateur().getId()
            );
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    public Projet getById(Integer id) {
        Utilisateur utilisateur = getCurrentUser();

        Projet p = repo.findById(id).orElse(null);
        if (p == null) return null;

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return p;
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            if (p.getStage().getStagiaire().getFormateur() != null &&
                p.getStage().getStagiaire().getFormateur().getId()
                        .equals(utilisateur.getFormateur().getId())) {

                return p;
            }
            throw new RessourceAccessDeniedException("Vous n'avez pas accès à ce projet");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    public Projet save(Projet p) {
        Utilisateur utilisateur = getCurrentUser();

        // ADMIN
        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.save(p);
        }

        // FORMATEUR
        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            // Stage obligatoire
            if (p.getStage() == null || p.getStage().getId() == null) {
                throw new RessourceAccessDeniedException("Un stage est obligatoire");
            }

            // 🔥 Recharger le stage depuis la BDD
            Stage stage = stageRepo.findById(p.getStage().getId())
                    .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

            // 🔥 Vérifier que le stage appartient au formateur connecté
            if (stage.getStagiaire() == null ||
                stage.getStagiaire().getFormateur() == null ||
                !stage.getStagiaire().getFormateur().getId()
                        .equals(utilisateur.getFormateur().getId())) {

                throw new RessourceAccessDeniedException("Ce stage appartient à un autre formateur");
            }

            // 🔥 Pour une modification
            if (p.getId() != null) {
                Projet existant = repo.findById(p.getId()).orElse(null);

                if (existant != null &&
                    existant.getStage() != null &&
                    existant.getStage().getStagiaire() != null &&
                    existant.getStage().getStagiaire().getFormateur() != null &&
                    !existant.getStage().getStagiaire().getFormateur().getId()
                            .equals(utilisateur.getFormateur().getId())) {

                    throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier ce projet");
                }
            }

            // Associer le bon stage
            p.setStage(stage);

            return repo.save(p);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    public void delete(Integer id) {
        Utilisateur utilisateur = getCurrentUser();

        Projet p = repo.findById(id).orElse(null);
        if (p == null) return;

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            repo.deleteById(id);
            return;
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {
            if (p.getStage().getStagiaire().getFormateur() != null &&
                p.getStage().getStagiaire().getFormateur().getId()
                        .equals(utilisateur.getFormateur().getId())) {

                repo.deleteById(id);
                return;
            }
            throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer ce projet");
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    public List<Projet> saveAll(List<Projet> projets) {
        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin) {
            return repo.saveAll(projets);
        }

        if (utilisateur.getRole() == RoleUtilisateur.formateur) {

            for (Projet p : projets) {

                // 🔥 Même correction : recharger le stage
                Stage stage = stageRepo.findById(p.getStage().getId())
                        .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

                if (!stage.getStagiaire().getFormateur().getId()
                        .equals(utilisateur.getFormateur().getId())) {

                    throw new RessourceAccessDeniedException(
                            "Un des projets ne vous appartient pas"
                    );
                }

                // Attacher le stage complet
                p.setStage(stage);
            }

            return repo.saveAll(projets);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }
}
