package com.gestion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Reunion;
import com.gestion.service.ReunionService;

@RestController
@RequestMapping("/api/reunions")
@CrossOrigin(origins = "http://localhost:5173")
public class ReunionRestController {

    private final ReunionService service;

    public ReunionRestController(ReunionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Reunion> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Reunion getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Reunion create(@RequestBody Reunion r) {
        return service.create(r);
    }

    @PutMapping("/{id}")
    public Reunion update(@PathVariable Integer id, @RequestBody Reunion r) {
        return service.update(id, r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
