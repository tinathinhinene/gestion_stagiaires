package com.gestion.controller;

import com.gestion.entity.Stage;
import com.gestion.service.StageService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stages")
@CrossOrigin(origins = "http://localhost:5173")
public class StageRestController {

    private final StageService service;

    public StageRestController(StageService service) {
        this.service = service;
    }

    // ==========================
    // GET ALL (sécurisé)
    // ==========================
    @GetMapping
    public List<Stage> getAll() {
        return service.getAll();   // ✔️ utilise la sécurité du service
    }

    // ==========================
    // GET BY ID (sécurisé)
    // ==========================
    @GetMapping("/{id}")
    public Stage getById(@PathVariable Integer id) {
        return service.getById(id);  // ✔️ utilise le service
    }

    // ==========================
    // CREATE (sécurisé)
    // ==========================
    @PostMapping
    public Stage create(@RequestBody Stage s) {
        return service.save(s);   // ✔️ utilise la sécurité du service
    }

    // ==========================
    // UPDATE (obligatoire !)
    // ==========================
    @PutMapping("/{id}")
    public Stage update(@PathVariable Integer id, @RequestBody Stage s) {
        return service.update(id, s);  // ✔️ ne pas utiliser save !!
    }

    // ==========================
    // DELETE (sécurisé)
    // ==========================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);      // ✔️ utilise la sécurité du service
    }
}
