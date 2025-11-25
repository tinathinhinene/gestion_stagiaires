package com.gestion.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Stagiaire;
import com.gestion.service.StagiaireService; // controller utilise service pour parler a bdd

@RestController      // renvoi une réponse en JSON
@RequestMapping("/api/stagiaires")                //toute les routes commence par api/stagiaire
@CrossOrigin(origins = "http://localhost:5173")    // autorise react qui tourne sur 5173 a acceder  a api spring boot (port8021)

public class StagiaireRestController {  //declare ma classe et je defini mes routes http
    
	private final StagiaireService service;    //controller passe par service pour parler au repository                 
	
	public StagiaireRestController(StagiaireService service) {  //constructeur
				this.service = service;
	}
	//ENDPOINT QUI RECUPERE TOTU
	@GetMapping //quand qlq1 envoi requete http get sur url /api/stagiaire cette methode s'execute
	public List<Stagiaire> getAll() { // spring appelle getall(), getall() appelle service, service appele ripo.findall(), repository interoge bdd, et liste trouvé renvoyé
	return  service.getAll(); 
			}
	
	// ENDPOINT RECUPERE UN SEUL STAGIAIRE
	@GetMapping("/{id}") //url contient parametre dynamique         
	public Stagiaire getById(@PathVariable Integer id) {   //spring voit id dans l'url et le met dans la variable id java
	return service.getById(id);    //appelle le service qui cherche le stagiaire dans la base	
			}
	
	//ENDPOINT POUR CREER
	@PostMapping    //repond a POST/api/stagiaires
	public Stagiaire create(@RequestBody Stagiaire s) {  //recupere le JSON qui arrive dans le corps de la requete et le transformer en objet stagiaire
		return service.save(s); //enregistre le stagiaire dans la base
					}
	
	 @PostMapping("/bulk")
	    public List<Stagiaire> createAll(@RequestBody List<Stagiaire> stagiaires) {
	        return service.saveAll(stagiaires);
	    }
	 
	//ENDPOINT POUR MODIFIER    
	@PutMapping("/{id}") //pour modifier un stagiaire existant repond à PUT /api/stagiaires
	public Stagiaire update(@PathVariable Integer id, @RequestBody Stagiaire s) {  // le path variablle recupere l'id dans l'url met la dans variable id, request body dit a spring  le json envoyé par le client trnsforme le en objet java stagiaire
		s.setId(id); //association de id a objet stagiaire
		return service.save(s); //servicesave met a jour dans bdd et renvoi 200 stagiaire mis a jour
			}
	
	//ENDPOINT POUR SUPPRIMER
	@DeleteMapping("/{id}") //repond a delete/api/stagiaires/5
	public void delete(@PathVariable Integer id) {//methode ne renvoi aucun corps juste le code 204
		service.delete(id); //supprime le stagiaire
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


