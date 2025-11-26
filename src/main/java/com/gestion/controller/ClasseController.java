package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gestion.entity.Classe;
import com.gestion.service.ClasseService;

@Controller
@RequestMapping("/classes")
public class ClasseController {

    private final ClasseService service;

    public ClasseController(ClasseService service) {
        this.service = service;
    }

    // LISTE
    @GetMapping
    public String list(Model model) {
        model.addAttribute("classes", service.getAll());
        return "classes";
    }

    // FORM CREATE
    @GetMapping("/new")
    public String formNew(Model model) {
        model.addAttribute("classe", new Classe());
        return "classe_form";
    }

    // CREATE (admin + formateur)
    @PostMapping("/create")
    public String create(@ModelAttribute("classe") Classe classe) {
        service.create(classe);
        return "redirect:/classes";
    }

    // FORM EDIT (admin)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("classe", service.getById(id));
        return "classe_form";
    }

    // UPDATE (admin)
    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("classe") Classe classe) {

        service.update(id, classe);
        return "redirect:/classes";
    }

    // DELETE (admin)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/classes";
    }
}
