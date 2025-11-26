package com.gestion.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Formateur;
import com.gestion.service.FormateurService;

@RestController
@RequestMapping("/api/formateurs")
@CrossOrigin(origins = "http://localhost:5173")
public class FormateurRestController {

    private final FormateurService service;

    public FormateurRestController(FormateurService service) {
        this.service = service;
    }

    @GetMapping
    public List<Formateur> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Formateur getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Formateur create(@RequestBody Formateur f) {
        return service.save(f);
    }

    @PutMapping("/{id}")
    public Formateur update(@PathVariable Integer id, @RequestBody Formateur f) {
        return service.update(id, f);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
