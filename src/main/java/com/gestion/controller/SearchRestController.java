package com.gestion.controller;

import com.gestion.dto.SearchResponse;
import com.gestion.repository.StagiaireRepository;
import com.gestion.repository.FormateurRepository;
import com.gestion.repository.ProjetRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
public class SearchRestController {

    private final StagiaireRepository stagiaireRepo;
    private final FormateurRepository formateurRepo;
    private final ProjetRepository projetRepo;

    public SearchRestController(
            StagiaireRepository stagiaireRepo,
            FormateurRepository formateurRepo,
            ProjetRepository projetRepo
    ) {
        this.stagiaireRepo = stagiaireRepo;
        this.formateurRepo = formateurRepo;
        this.projetRepo = projetRepo;
    }

    @GetMapping
    public SearchResponse search(@RequestParam("q") String q) {

        String query = q.trim();

        var stagiaires = stagiaireRepo
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);

        var formateurs = formateurRepo
                .findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(query, query);

        var projets = projetRepo
                .findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);

        return new SearchResponse(query, stagiaires, formateurs, projets);
    }
}
