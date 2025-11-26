package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Centre;
import com.gestion.service.CentreService;

@Controller
@RequestMapping("/centres")
public class CentreController {

    private final CentreService service;

    public CentreController(CentreService service) {
        this.service = service;
    }

    // ======================================================
    // LISTE
    // ======================================================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("centres", service.getAll());
        return "centres";
    }

    // ======================================================
    // FORMULAIRE DE CREATION
    // ======================================================
    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("centre", new Centre());
        return "centre_form";
    }

    // ======================================================
    // CREATION (ADMIN + FORMATEUR)
    // ======================================================
    @PostMapping("/create")
    public String create(@ModelAttribute("centre") Centre centre) {
        service.create(centre);
        return "redirect:/centres";
    }

    // ======================================================
    // FORMULAIRE DE MODIFICATION (ADMIN SEULEMENT)
    // ======================================================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Centre centre = service.getById(id);
        model.addAttribute("centre", centre);
        return "centre_form";
    }

    // ======================================================
    // MISE À JOUR (ADMIN SEULEMENT)
    // ======================================================
    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("centre") Centre centre) {
        service.update(id, centre);
        return "redirect:/centres";
    }

    // ======================================================
    // SUPPRESSION (ADMIN SEULEMENT)
    // ======================================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/centres";
    }
}
