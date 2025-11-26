package com.gestion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Utilisateur;
import com.gestion.service.UtilisateurService;

@RestController
@RequestMapping("/api/utilisateurs")
@CrossOrigin(origins = "http://localhost:5173")
public class UtilisateurRestController {

    private final UtilisateurService service;

    public UtilisateurRestController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Utilisateur> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Utilisateur create(@RequestBody Utilisateur u) {
        return service.create(u);
    }

    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Integer id, @RequestBody Utilisateur u) {
        return service.update(id, u);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
