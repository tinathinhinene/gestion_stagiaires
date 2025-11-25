package com.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gestion.entity.Document;
import com.gestion.repository.DocumentRepository;

@Service
public class DocumentService {

    private final DocumentRepository repo;

    public DocumentService(DocumentRepository repo) {
        this.repo = repo;
    }

    public List<Document> getAll() {
        return repo.findAll();
    }

    public Document getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Document save(Document d) {
        return repo.save(d);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

	public List<Document> saveAll(List<Document> documents) {
		 return repo.saveAll(documents);
	}
}
