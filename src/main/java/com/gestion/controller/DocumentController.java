package com.gestion.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.gestion.dto.DocumentRequest;
import com.gestion.exception.FichierStockageException;
import com.gestion.service.DocumentService;
import com.gestion.service.StageService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final StageService stageService;

    public DocumentController(DocumentService documentService, StageService stageService) {
        this.documentService = documentService;
        this.stageService = stageService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("documents", documentService.getAll());
        return "documents";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("document", new DocumentRequest());
        model.addAttribute("stages", stageService.getAll());
        return "document_form";
    }

    @PostMapping("/create")
    public String save(@RequestParam("file") MultipartFile file,
                       @RequestParam("nom") String nom,
                       @RequestParam("type") String type,
                       @RequestParam("stageId") Integer stageId) {

        try {
            String uploadDir = "uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String filePath = uploadDir + file.getOriginalFilename();
            Files.write(Paths.get(filePath), file.getBytes());

            DocumentRequest req = new DocumentRequest();
            req.setNom(nom);
            req.setType(type);
            req.setChemin(filePath);

            DocumentRequest.StageIdDTO stageDto = new DocumentRequest.StageIdDTO();
            stageDto.setId(stageId);
            req.setStage(stageDto);

            documentService.create(req);

        } catch (IOException e) {
            throw new FichierStockageException("Erreur lors de l’upload du fichier", e);
        }

        return "redirect:/documents";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        documentService.delete(id);
        return "redirect:/documents";
    }
}
