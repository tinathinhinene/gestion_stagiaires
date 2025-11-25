package com.gestion.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Document;
import com.gestion.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:5173")

public class DocumentRestController {

    private final DocumentService service;

    public DocumentRestController(DocumentService service) {
        this.service = service;
    }

    @GetMapping
    public List<Document> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Document getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PutMapping("/update/{id}")
    public Document update(@PathVariable Integer id, @RequestBody Document d) {
        d.setId(id);
        return service.save(d);
    }
    
    @PostMapping("/create")
    public Document create(@RequestBody Document d) {
        return service.save(d);
    }

    @PostMapping("/bulk")
    public List<Document> createAll(@RequestBody List<Document> documents) {
        return service.saveAll(documents);
    }

    @PostMapping
    public Document save(@RequestBody Document d) {
        return service.save(d);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}



















