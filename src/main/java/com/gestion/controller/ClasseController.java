package com.gestion.controller;

import com.gestion.entity.Classe;
import com.gestion.service.ClasseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/classes")
public class ClasseController {

//=============================================================================
    //attributs
//=============================================================================

	private final ClasseService service;
	
//=============================================================================
	                   //constructeurs
//=============================================================================

    public ClasseController(ClasseService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("classes", service.getAll());
        return "classes";
    }

    @GetMapping("/new")
    public String formClasse(Model model) {
        model.addAttribute("classe", new Classe());
        return "classe_form"; 
    }

    @PostMapping
    public String save(@ModelAttribute("classe") Classe classe) {
        service.save(classe);
        return "redirect:/classes"; 
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/classes";
    }
}
