package com.gestion.controller;

import com.gestion.entity.Reunion;
import com.gestion.entity.TypeReunion;
import com.gestion.entity.EtatReunion;
import com.gestion.service.ReunionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reunions")
public class ReunionController {

//=============================================================================
    //attributs
//=============================================================================
	
    private final ReunionService service;

  //=============================================================================
    //constructeurs
//=============================================================================
    
    public ReunionController(ReunionService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reunions", service.getAll());
        return "reunions";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("reunion", new Reunion());
        model.addAttribute("types", TypeReunion.values());
        model.addAttribute("etats", EtatReunion.values());
        return "reunion_form";
    }

    @PostMapping
    public String save(@ModelAttribute Reunion reunion) {
        service.save(reunion);
        return "redirect:/reunions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Reunion r = service.getById(id);
        if (r != null) {
            model.addAttribute("reunion", r);
            model.addAttribute("types", TypeReunion.values());
            model.addAttribute("etats", EtatReunion.values());
            return "reunion_form";
        }
        return "redirect:/reunions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/reunions";
    }
}
