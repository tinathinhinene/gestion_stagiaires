package com.gestion.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gestion.entity.Classe;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.entity.Utilisateur;
import com.gestion.exception.RessourceAccessDeniedException;
import com.gestion.repository.ClasseRepository;

@Service
public class ClasseService {

    private final ClasseRepository repo;

    public ClasseService(ClasseRepository repo) {
        this.repo = repo;
    }

    // Récupération de l'utilisateur connecté
    private Utilisateur getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Utilisateur) auth.getPrincipal();
    }

    // ======================================================
    // GET ALL (admin + formateur)
    // ======================================================
    public List<Classe> getAll() {
        return repo.findAll();
    }

    // ======================================================
    // GET BY ID (admin + formateur)
    // ======================================================
    public Classe getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // ======================================================
    // CREATE (admin + formateur)
    // ======================================================
    public Classe create(Classe classe) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() == RoleUtilisateur.admin ||
            utilisateur.getRole() == RoleUtilisateur.formateur) {

            return repo.save(classe);
        }

        throw new RessourceAccessDeniedException("Accès refusé");
    }

    // ======================================================
    // UPDATE (admin seulement)
    // ======================================================
    public Classe update(Integer id, Classe classe) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() != RoleUtilisateur.admin) {
            throw new RessourceAccessDeniedException("Vous ne pouvez pas modifier une classe");
        }

        classe.setId(id);
        return repo.save(classe);
    }

    // ======================================================
    // DELETE (admin seulement)
    // ======================================================
    public void delete(Integer id) {

        Utilisateur utilisateur = getCurrentUser();

        if (utilisateur.getRole() != RoleUtilisateur.admin) {
            throw new RessourceAccessDeniedException("Vous ne pouvez pas supprimer une classe");
        }

        repo.deleteById(id);
    }
}
