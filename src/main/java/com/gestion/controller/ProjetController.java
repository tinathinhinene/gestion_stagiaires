package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gestion.entity.Projet;
import com.gestion.service.ProjetService;

@Controller
@RequestMapping("/projets")

public class ProjetController {

//=============================================================================
		            //attributs
//	=============================================================================
  
	private final ProjetService service;

//=============================================================================
    //constructeurs
//=============================================================================
   
	public ProjetController(ProjetService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("projets", service.getAll());
        return "projets";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("projet", new Projet());
        return "projet_form";
    }

    @PostMapping
    public String save(@ModelAttribute ("projet") Projet projet) {
        service.save(projet);
        return "redirect:/projets";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/projets";
    }
}
