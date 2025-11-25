package com.gestion.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gestion.entity.Centre;

import com.gestion.repository.CentreRepository;

@Service
public class CentreService {

	private final CentreRepository repo;

	public CentreService(CentreRepository repo) {
		this.repo = repo;
	}
	
	public List<Centre> getAll() { 
		return repo.findAll(); 
		}
	
 public Centre getById(Integer id) { 
	 return repo.findById(id).orElse(null);
	 }
 
public Centre save(Centre c) { 
	return repo.save(c);
	}

public void delete(Integer id) {
	repo.deleteById(id);
	}

public List<Centre> saveAll(List<Centre> centres) {
	 return repo.saveAll(centres);
		
}

}
