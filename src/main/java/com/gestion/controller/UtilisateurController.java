package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gestion.entity.Utilisateur;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.service.UtilisateurService;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {
	
	//=============================================================================
    //attributs
//=============================================================================
    
	private final UtilisateurService service;

	//=============================================================================
	                   //constructeurs
	//=============================================================================
   
	public UtilisateurController(UtilisateurService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("utilisateurs", service.getAll());
        return "utilisateurs";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", RoleUtilisateur.values());
        return "utilisateur_form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model) {
        Utilisateur u = service.getById(id);

        if (u == null) {
            return "redirect:/utilisateurs";
        }

        model.addAttribute("utilisateur", u);
        model.addAttribute("roles", RoleUtilisateur.values());
        return "utilisateur_form";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/utilisateurs";
    }
}
