package com.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gestion.entity.Formateur;
import com.gestion.repository.FormateurRepository;

@Service
public class FormateurService {

    private final FormateurRepository repo;

    public FormateurService(FormateurRepository repo) {
        this.repo = repo;
    }

    public List<Formateur> getAll() {
        return repo.findAll();
    }

    public Formateur getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Formateur save(Formateur f) {
        return repo.save(f);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

	public List<Formateur> saveAll(List<Formateur> formateurs) {
		 return repo.saveAll(formateurs);
	}
}
