package com.gestion.service;

import com.gestion.entity.Classe;
import com.gestion.repository.ClasseRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClasseService {

    private final ClasseRepository repo;

    public ClasseService(ClasseRepository repo) {
        this.repo = repo;
    }

    public List<Classe> getAll() {
        return repo.findAll();
    }

    // 🔹 Trouver une classe par ID
    public Classe getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    // 🔹 Créer ou modifier une classe
    public Classe save(Classe c) {
        return repo.save(c);
    }

    // 🔹 Supprimer une classe
    public void delete(Integer id) {
        repo.deleteById(id);
    }

	public List<Classe> saveAll(List<Classe> classes) {
		 return repo.saveAll(classes);
		
	}

	public void deleteAll() {
	    repo.deleteAll();
	}
}
