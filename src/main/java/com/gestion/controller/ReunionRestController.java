package com.gestion.controller;

import com.gestion.entity.Reunion;
import com.gestion.service.ReunionService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    public Reunion getOne(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Reunion create(@RequestBody Reunion r) {
        return service.save(r);
    }

    @PostMapping("/bulk")
    public List<Reunion> createAll(@RequestBody List<Reunion> reunions) {
        return service.saveAll(reunions);
    }
    
    @PutMapping("/{id}")
    public Reunion update(@PathVariable Integer id, @RequestBody Reunion r) {
        r.setId(id);
        return service.save(r);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
