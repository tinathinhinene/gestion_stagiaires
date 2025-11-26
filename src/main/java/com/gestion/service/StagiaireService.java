package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.dto.StagiaireInput;
import com.gestion.entity.Classe;
import com.gestion.entity.Formateur;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Stagiaire;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.ClasseRepository;
import com.gestion.repository.FormateurRepository;
import com.gestion.repository.StagiaireRepository;

@Service
public class StagiaireService {

    private final StagiaireRepository repo;
    private final FormateurRepository formateurRepo;
    private final ClasseRepository classeRepo;

    public StagiaireService(StagiaireRepository repo,
                            FormateurRepository formateurRepo,
                            ClasseRepository classeRepo) {
        this.repo = repo;
        this.formateurRepo = formateurRepo;
        this.classeRepo = classeRepo;
    }

    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ============================================================
    // GET ALL
    // ============================================================
    public List<Stagiaire> getAll() {
        Utilisateur u = getCurrentUser();

        if (u.getRole() == RoleUtilisateur.admin) {
            return repo.findAll();
        }

        if (u.getRole() == RoleUtilisateur.formateur) {
            return repo.findByFormateur_Id(u.getFormateur().getId());
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ============================================================
    // GET BY ID
    // ============================================================
    public Stagiaire getById(Integer id) {
        Utilisateur u = getCurrentUser();

        Stagiaire s = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stagiaire introuvable"));

        if (u.getRole() == RoleUtilisateur.admin) return s;

        if (u.getRole() == RoleUtilisateur.formateur &&
            s.getFormateur().getId().equals(u.getFormateur().getId())) {
            return s;
        }

        throw new RessourceAccessDeniedException("Accès interdit");
    }

    // ============================================================
    // CREATE
    // ============================================================
    public Stagiaire create(StagiaireInput req) {

        Utilisateur u = getCurrentUser();

        if (req.getFormateur() == null || req.getFormateur().getId() == null)
            throw new RessourceAccessDeniedException("Formateur obligatoire");

        if (req.getClasse() == null || req.getClasse().getId() == null)
            throw new RessourceAccessDeniedException("Classe obligatoire");

        Formateur formateur = formateurRepo.findById(req.getFormateur().getId())
                .orElseThrow(() -> new RessourceAccessDeniedException("Formateur introuvable"));

        Classe classe = classeRepo.findById(req.getClasse().getId())
                .orElseThrow(() -> new RessourceAccessDeniedException("Classe introuvable"));

        // FORMATEUR → peut créer seulement ses stagiaires
        if (u.getRole() == RoleUtilisateur.formateur &&
           !formateur.getId().equals(u.getFormateur().getId())) {
            throw new RessourceAccessDeniedException("Ce formateur ne vous appartient pas");
        }

        Stagiaire s = new Stagiaire();
        s.setNom(req.getNom());
        s.setPrenom(req.getPrenom());
        s.setEmail(req.getEmail());
        s.setTel(req.getTel());
        s.setDateNaiss(req.getDateNaiss());
        s.setActif(req.getActif());
        s.setFormateur(formateur);
        s.setClasse(classe);

        return repo.save(s);
    }

    // ============================================================
    // UPDATE
    // ============================================================
    public Stagiaire update(Integer id, StagiaireInput req) {

        Utilisateur u = getCurrentUser();

        Stagiaire s = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stagiaire introuvable"));

        // FORMATEUR ne peut modifier que ses stagiaires
        if (u.getRole() == RoleUtilisateur.formateur &&
           !s.getFormateur().getId().equals(u.getFormateur().getId())) {
            throw new RessourceAccessDeniedException("Modification interdite");
        }

        // modification du formateur (admin uniquement)
        if (req.getFormateur() != null && req.getFormateur().getId() != null) {

            Formateur f = formateurRepo.findById(req.getFormateur().getId())
                    .orElseThrow(() -> new RessourceAccessDeniedException("Formateur introuvable"));

            if (u.getRole() == RoleUtilisateur.formateur &&
                !f.getId().equals(u.getFormateur().getId())) {
                throw new RessourceAccessDeniedException("Vous ne pouvez pas changer le formateur");
            }

            s.setFormateur(f);
        }

        // modification de la classe
        if (req.getClasse() != null && req.getClasse().getId() != null) {

            Classe c = classeRepo.findById(req.getClasse().getId())
                    .orElseThrow(() -> new RessourceAccessDeniedException("Classe introuvable"));

            s.setClasse(c);
        }

        s.setNom(req.getNom());
        s.setPrenom(req.getPrenom());
        s.setEmail(req.getEmail());
        s.setTel(req.getTel());
        s.setDateNaiss(req.getDateNaiss());
        s.setActif(req.getActif());

        return repo.save(s);
    }

    // ============================================================
    // DELETE
    // ============================================================
    public void delete(Integer id) {
        Utilisateur u = getCurrentUser();

        Stagiaire s = repo.findById(id)
                .orElseThrow(() -> new RessourceAccessDeniedException("Stagiaire introuvable"));

        if (u.getRole() == RoleUtilisateur.admin ||
           (u.getRole() == RoleUtilisateur.formateur &&
            s.getFormateur().getId().equals(u.getFormateur().getId()))) {

            repo.deleteById(id);
            return;
        }

        throw new RessourceAccessDeniedException("Suppression interdite");
    }
}
