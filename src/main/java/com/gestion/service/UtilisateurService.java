package com.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gestion.entity.Utilisateur;
import com.gestion.repository.UtilisateurRepository;

@Service
public class UtilisateurService {

    private final UtilisateurRepository repo;

    public UtilisateurService(UtilisateurRepository repo) {
        this.repo = repo;
    }

    public List<Utilisateur> getAll() {
        return repo.findAll();
    }

    public Utilisateur getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Utilisateur save(Utilisateur u) {
        return repo.save(u);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

	public List<Utilisateur> saveAll(List<Utilisateur> utilisateurs) {
		 return repo.saveAll(utilisateurs);
	}
}
