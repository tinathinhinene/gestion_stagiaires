package com.gestion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Commentaire;
import com.gestion.service.CommentaireService;

@RestController
@RequestMapping("/api/commentaires")
@CrossOrigin(origins = "http://localhost:5173")
public class CommentaireRestController {

    private final CommentaireService service;

    public CommentaireRestController(CommentaireService service) {
        this.service = service;
    }

    @GetMapping
    public List<Commentaire> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Commentaire getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Commentaire create(@RequestBody Commentaire commentaire) {
        return service.create(commentaire);
    }

    @PutMapping("/{id}")
    public Commentaire update(@PathVariable Integer id,
                              @RequestBody Commentaire commentaire) {
        return service.update(id, commentaire);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
