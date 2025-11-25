package com.gestion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.gestion.entity.Centre;
import com.gestion.service.CentreService;

@Controller
@RequestMapping("/centres")
public class CentreController {

	//=============================================================================
    //attributs
//=============================================================================
	
	private final CentreService service;

	//=============================================================================
    //constructeurs
//=============================================================================
	
	public CentreController(CentreService service) {
		this.service = service;
	}

	@GetMapping
	public String List(Model model) {
		model.addAttribute("centres", service.getAll());
		return "centres";
	}

	@GetMapping("/new")
	public String form(Model model) {
		model.addAttribute("centre", new Centre());
		return "centre_form";
	}

	@PostMapping
	public String save(@ModelAttribute("centre") Centre centre) {
		service.save(centre);
		return "redirect:/centres";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		service.delete(id);
		return "redirect:/centres";
	}
	
}
