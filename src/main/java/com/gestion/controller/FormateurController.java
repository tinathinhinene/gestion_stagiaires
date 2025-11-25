package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gestion.entity.Formateur;
import com.gestion.service.FormateurService;

@Controller
@RequestMapping("/formateurs")

public class FormateurController {
	
	//=============================================================================
    //attributs
//=============================================================================
   
	private final FormateurService service;

	//=============================================================================
    //constructeurs
//=============================================================================
    
	public FormateurController(FormateurService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("formateurs", service.getAll());
        return "formateurs";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("formateur", new Formateur());
        return "formateur_form";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute ("formateur") Formateur formateur) {
        service.save(formateur);
        return "redirect:/formateurs";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/formateurs";
    }
}
