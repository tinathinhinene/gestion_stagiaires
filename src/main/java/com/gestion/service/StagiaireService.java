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
    // CREATE (OPTION 1)
    // Automatique : Formateur = formateur connecté
    // Classe = par défaut ID = 1 (à adapter)
    // ============================================================
    public Stagiaire create(StagiaireInput req) {

        Utilisateur u = getCurrentUser();

        // Le formateur connecté
        Formateur formateur = u.getFormateur();

        if (formateur == null)
            throw new RessourceAccessDeniedException("Un formateur doit être connecté");

        // Classe par défaut
        Classe classe = classeRepo.findById(1)
                .orElseThrow(() -> new RessourceAccessDeniedException("Classe par défaut introuvable"));

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

        // Le formateur ne peut modifier que ses stagiaires
        if (u.getRole() == RoleUtilisateur.formateur &&
            !s.getFormateur().getId().equals(u.getFormateur().getId())) {
            throw new RessourceAccessDeniedException("Modification interdite");
        }

        s.setNom(req.getNom());
        s.setPrenom(req.getPrenom());
        s.setEmail(req.getEmail());
        s.setTel(req.getTel());
        s.setDateNaiss(req.getDateNaiss());
        s.setActif(req.getActif());

        return repo.save(s);
    }
 // src/main/java/com/gestion/service/StagiaireService.java

 // ...

 // ============================================================
 // DELETE
 // ============================================================
 public void delete(Integer id) {
     Utilisateur u = getCurrentUser();

     Stagiaire s = repo.findById(id)
             .orElseThrow(() -> new RessourceAccessDeniedException("Stagiaire introuvable"));

     // ADMIN : peut tout supprimer
     if (u.getRole() == RoleUtilisateur.admin) {
         repo.deleteById(id);
         return;
     }

     // FORMATEUR : ne peut supprimer que ses stagiaires
     if (u.getRole() == RoleUtilisateur.formateur) {
         if (s.getFormateur() != null
                 && s.getFormateur().getId() != null
                 && s.getFormateur().getId().equals(u.getFormateur().getId())) {

             repo.deleteById(id);
             return;
         }

         throw new RessourceAccessDeniedException("Suppression interdite (stagiaire d'un autre formateur)");
     }

     // Tous les autres rôles : interdit
     throw new RessourceAccessDeniedException("Suppression interdite");
 }
}
 