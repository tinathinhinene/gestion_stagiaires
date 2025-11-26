package com.gestion.controller;

import com.gestion.entity.Reunion;
import com.gestion.entity.TypeReunion;
import com.gestion.entity.EtatReunion;
import com.gestion.service.ReunionService;
import com.gestion.service.StagiaireService;
import com.gestion.service.ProjetService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reunions")
public class ReunionController {

    private final ReunionService reunionService;
    private final StagiaireService stagiaireService;
    private final ProjetService projetService;

    // ============================================================
    // CONSTRUCTEUR
    // ============================================================
    public ReunionController(ReunionService reunionService, 
                             StagiaireService stagiaireService,
                             ProjetService projetService) {
        this.reunionService = reunionService;
        this.stagiaireService = stagiaireService;
        this.projetService = projetService;
    }

    // ============================================================
    // LISTE
    // ============================================================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("reunions", reunionService.getAll());
        return "reunions";
    }

    // ============================================================
    // FORMULAIRE CREATION
    // ============================================================
    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute("reunion", new Reunion());
        model.addAttribute("types", TypeReunion.values());
        model.addAttribute("etats", EtatReunion.values());

        // ✔ liste des stagiaires autorisés selon rôle
        model.addAttribute("stagiaires", stagiaireService.getAll());

        // ✔ liste des projets liés à leurs stagiaires
        model.addAttribute("projets", projetService.getAll());

        return "reunion_form";
    }

    // ============================================================
    // SAUVEGARDE
    // ============================================================
    @PostMapping
    public String save(@ModelAttribute Reunion reunion) {

        reunionService.save(reunion);
        return "redirect:/reunions";
    }

    // ============================================================
    // FORMULAIRE EDITION
    // ============================================================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Reunion r = reunionService.getById(id);

        if (r == null) {
            return "redirect:/reunions";
        }

        model.addAttribute("reunion", r);
        model.addAttribute("types", TypeReunion.values());
        model.addAttribute("etats", EtatReunion.values());

        model.addAttribute("stagiaires", stagiaireService.getAll());
        model.addAttribute("projets", projetService.getAll());

        return "reunion_form";
    }

    // ============================================================
    // SUPPRESSION
    // ============================================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        reunionService.delete(id);
        return "redirect:/reunions";
    }
}
