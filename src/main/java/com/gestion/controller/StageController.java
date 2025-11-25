package com.gestion.controller;

import com.gestion.entity.Stage;
import com.gestion.entity.EtatStage;
import com.gestion.service.StageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/stages")

public class StageController {
//=============================================================================
//attributs
//=============================================================================

    private final StageService service;
    
 //=============================================================================
    //constructeurs
//=============================================================================

    public StageController(StageService service) {
        this.service = service;
    }

  
    @GetMapping
    public String list(Model model) {
        model.addAttribute("stages", service.getAll());
        return "stages";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("stage", new Stage());
        model.addAttribute("etats", EtatStage.values());
        return "stage_form";
    }

    @PostMapping
    public String save(@ModelAttribute ("stage") Stage stage) {
        service.save(stage);
        return "redirect:/stages";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/stages";
    }
}
