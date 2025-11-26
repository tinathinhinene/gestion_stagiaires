package com.gestion.controller;

import com.gestion.dto.StagiaireInput;
import com.gestion.entity.Stagiaire;
import com.gestion.service.ClasseService;
import com.gestion.service.FormateurService;
import com.gestion.service.StagiaireService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stagiaires")
public class StagiaireController {

    private final StagiaireService service;
    private final FormateurService formateurService;
    private final ClasseService classeService;

    public StagiaireController(
            StagiaireService service,
            FormateurService formateurService,
            ClasseService classeService
    ) {
        this.service = service;
        this.formateurService = formateurService;
        this.classeService = classeService;
    }

    // ============================================================
    // LISTE
    // ============================================================
    @GetMapping
    public String list(Model model) {
        model.addAttribute("stagiaires", service.getAll());
        return "stagiaires";
    }

    // ============================================================
    // FORM CREATION
    // ============================================================
    @GetMapping("/new")
    public String form(Model model) {

        model.addAttribute("stagiaire", new Stagiaire());
        model.addAttribute("formateurs", formateurService.getAll());
        model.addAttribute("classes", classeService.getAll());

        return "stagiaire_form";
    }

    // ============================================================
    // FORM EDITION
    // ============================================================
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Stagiaire s = service.getById(id);
        if (s == null) return "redirect:/stagiaires";

        model.addAttribute("stagiaire", s);
        model.addAttribute("formateurs", formateurService.getAll());
        model.addAttribute("classes", classeService.getAll());

        return "stagiaire_form";
    }

    // ============================================================
    // SAVE (CREATE + UPDATE)
    // ============================================================
    @PostMapping
    public String save(@ModelAttribute("stagiaire") Stagiaire stagiaire) {

        // Convertir vers DTO StagiaireInput
        StagiaireInput dto = new StagiaireInput();

        dto.setNom(stagiaire.getNom());
        dto.setPrenom(stagiaire.getPrenom());
        dto.setEmail(stagiaire.getEmail());
        dto.setTel(stagiaire.getTel());
        dto.setDateNaiss(stagiaire.getDateNaiss());
        dto.setActif(stagiaire.getActif());

        // --------------------------
        // FORMATEUR
        // --------------------------
        if (stagiaire.getFormateur() != null && stagiaire.getFormateur().getId() != null) {
            StagiaireInput.MiniObject f = new StagiaireInput.MiniObject();
            f.setId(stagiaire.getFormateur().getId());
            dto.setFormateur(f);
        }

        // --------------------------
        // CLASSE
        // --------------------------
        if (stagiaire.getClasse() != null && stagiaire.getClasse().getId() != null) {
            StagiaireInput.MiniObject c = new StagiaireInput.MiniObject();
            c.setId(stagiaire.getClasse().getId());
            dto.setClasse(c);
        }

        // --------------------------
        // CREATE OR UPDATE
        // --------------------------
        if (stagiaire.getId() == null) {
            service.create(dto);
        } else {
            service.update(stagiaire.getId(), dto);
        }

        return "redirect:/stagiaires";
    }

    // ============================================================
    // DELETE
    // ============================================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/stagiaires";
    }
}
