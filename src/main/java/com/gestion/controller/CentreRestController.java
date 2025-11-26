package com.gestion.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Centre;
import com.gestion.service.CentreService;

@RestController
@RequestMapping("/api/centres")
@CrossOrigin(origins = "http://localhost:5173")
public class CentreRestController {

    private final CentreService service;

    public CentreRestController(CentreService service) {
        this.service = service;
    }

    @GetMapping
    public List<Centre> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Centre getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Centre create(@RequestBody Centre centre) {
        return service.create(centre);
    }

    @PutMapping("/{id}")
    public Centre update(@PathVariable Integer id, @RequestBody Centre centre) {
        return service.update(id, centre);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
