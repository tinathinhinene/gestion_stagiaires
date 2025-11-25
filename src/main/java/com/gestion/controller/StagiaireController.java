package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gestion.entity.Stagiaire;
import com.gestion.service.StagiaireService;

@Controller                           // controller MVC qui renvoi une vue html
@RequestMapping("/stagiaires")         // les routes commencent par /stagiaires
public class StagiaireController {    //je declare ma classe

//=============================================================================
	            //attributs
//	=============================================================================
	
private final StagiaireService service;    

//=============================================================================
                   //constructeurs
//=============================================================================

public StagiaireController(StagiaireService service) {    //Spring te donne automatiquement un StagiaireService et tu le gardes dans ton contrôleur. injection de dependance
this.service = service;     //sauvegarde l'objet dans cet attribut
}


@GetMapping   // repond à get/stagiaires
public String list(Model model) {        // ici qu'on met les donnés pour la page
	model.addAttribute("stagiaires", service.getAll());     //jen envoi la liste a la vu sous nom stagiaires
	return "stagiaires";   //correspond à template stagiaire.html
	}


@GetMapping("/new")  //repond a get/stagiaires/new
public String form(Model model) { // affiche un formulaire d'ajout 
	model.addAttribute("stagiaire", new Stagiaire()); // dans le model en met stagiaire vide thymleaf va l'utiliser pour remplir les champs du formulaire
	return "stagiaire_form";     // spring affiche la page stagiaire_form.html
	}


@PostMapping //repond a post/stagiaires
public String save(@ModelAttribute ("stagiaire") Stagiaire stagiaire) {    // spring va prendreles champs du formulaire et les mettre dansun objet java stagiaire
	service.save(stagiaire); //en enregistre dans bdd
	return "redirect:/stagiaires"; // après enregistrer en revient à la liste 
	}


@GetMapping("/delete/{id}")    // repond a get/stagiaires/delete/id
public String delete(@PathVariable Integer id) { //recupere l i d qui es dans l url
	service.delete(id); //je demande au service de supprimer en bdd
	return "redirect:/stagiaires"; //reviens a la liste
	
}


}

























