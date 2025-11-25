package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gestion.entity.Commentaire;
import com.gestion.service.CommentaireService;

@Controller
@RequestMapping("/commentaires")
public class CommentaireController {

//=============================================================================
		            //attributs
//=============================================================================
  
	private final CommentaireService service;

//=============================================================================
	                   //constructeurs
//=============================================================================
    public CommentaireController(CommentaireService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
       
        model.addAttribute("commentaires", service.getAll());
        return "commentaires";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("commentaire", new Commentaire());
        return "commentaire_form";
    }

    @PostMapping
    public String save(@ModelAttribute ("Commentaire") Commentaire commentaire) {
        service.save(commentaire);
        return "redirect:/commentaires";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/commentaires";
    }
}
