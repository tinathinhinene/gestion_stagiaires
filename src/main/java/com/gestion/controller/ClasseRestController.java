package com.gestion.controller;

import com.gestion.entity.Classe;
import com.gestion.service.ClasseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "http://localhost:5173") 

public class ClasseRestController {

    private final ClasseService service;

    public ClasseRestController(ClasseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Classe> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Classe getById(@PathVariable Integer id) {
        return service.getById(id);
    }
    
    @PostMapping
    public Classe create(@RequestBody Classe c) {
        return service.save(c);
    }
    
    @PostMapping("/bulk")
    public List<Classe> createAll(@RequestBody List<Classe> classes) {
        return service.saveAll(classes);
    }

    @PutMapping("/{id}")
    public Classe update(@PathVariable Integer id, @RequestBody Classe c) {
        c.setId(id);
        return service.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
    
    @DeleteMapping("/clear")
    public void clear() {
        service.deleteAll();
    }

}
