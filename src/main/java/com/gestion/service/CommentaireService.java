package com.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gestion.entity.Commentaire;
import com.gestion.repository.CommentaireRepository;

@Service
public class CommentaireService {

    private final CommentaireRepository repo;

    public CommentaireService(CommentaireRepository repo) {
        this.repo = repo;
    }

    public List<Commentaire> getAll() {
        return repo.findAll();
    }

    public Commentaire getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Commentaire save(Commentaire c) {
        return repo.save(c);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

	public List<Commentaire> saveAll(List<Commentaire> commentaires) {
		 return repo.saveAll(commentaires);
	}
}
