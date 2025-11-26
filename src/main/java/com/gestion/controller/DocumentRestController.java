package com.gestion.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.gestion.dto.DocumentRequest;
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

    @PostMapping("/create")
    public Document create(@RequestBody DocumentRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public Document update(@PathVariable Integer id, @RequestBody DocumentRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
