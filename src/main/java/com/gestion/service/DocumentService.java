package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.dto.DocumentRequest;
import com.gestion.entity.Document;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Stage;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.DocumentRepository;
import com.gestion.repository.StageRepository;

@Service
public class DocumentService {

    private final DocumentRepository repo;
    private final StageRepository stageRepo;

    public DocumentService(DocumentRepository repo, StageRepository stageRepo) {
        this.repo = repo;
        this.stageRepo = stageRepo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ============================================================
    // GET ALL
    // ============================================================
    public List<Document> getAll() {
        Utilisateur u = getCurrentUser();

        if (u.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            return repo.findByStage_Stagiaire_Formateur_Id(u.getFormateur().getId());
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    public Document getById(Integer id) {
        Utilisateur u = getCurrentUser();

        Document d = repo.findById(id).orElse(null);
        if (d == null) return null;

        if (u.getRole() == RoleUtilisateur.admin) return d;

        if (u.getRole() == RoleUtilisateur.formateur &&
            d.getStage().getStagiaire().getFormateur().getId()
                .equals(u.getFormateur().getId())) {
            return d;
        }

        throw new RessourceAccessDeniedException("Accès au document interdit");
    }

    // ============================================================
    // CREATE
    // ============================================================
    public Document create(DocumentRequest req) {

        Utilisateur u = getCurrentUser();

        // Vérification Stage
        if (req.getStage() == null || req.getStage().getId() == null) {
            throw new RessourceAccessDeniedException("Stage obligatoire");
        }

        Stage stage = stageRepo.findById(req.getStage().getId())
                .orElseThrow(() -> new RessourceAccessDeniedException("Stage introuvable"));

        // Sécurité formateur
        if (u.getRole() == RoleUtilisateur.formateur &&
            !stage.getStagiaire().getFormateur().getId()
                .equals(u.getFormateur().getId())) {

            throw new RessourceAccessDeniedException("Ce stagiaire appartient à un autre formateur");
        }

        // Création
        Document d = new Document();
        d.setNom(req.getNom());
        d.setType(req.getType());
        d.setChemin(req.getChemin());
        d.setStage(stage);

        return repo.save(d);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    public Document update(Integer id, DocumentRequest req) {

        Utilisateur u = getCurrentUser();

        Document d = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Document introuvable"));

        // Sécurité formateur
        if (u.getRole() == RoleUtilisateur.formateur &&
            !d.getStage().getStagiaire().getFormateur().getId()
                .equals(u.getFormateur().getId())) {

            throw new RessourceAccessDeniedException("Modification interdite");
        }

        d.setNom(req.getNom());
        d.setType(req.getType());
        d.setChemin(req.getChemin());

        return repo.save(d);
    }

    // ============================================================
    // DELETE
    // ============================================================
    public void delete(Integer id) {

        Utilisateur u = getCurrentUser();

        Document d = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Document introuvable"));

        if (u.getRole() == RoleUtilisateur.admin ||
            (u.getRole() == RoleUtilisateur.formateur &&
             d.getStage().getStagiaire().getFormateur().getId()
                .equals(u.getFormateur().getId()))) {

            repo.delete(d);
            return;
        }

        throw new RessourceAccessDeniedException("Suppression interdite");
    }
}
