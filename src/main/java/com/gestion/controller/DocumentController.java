package com.gestion.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.gestion.entity.Document;
import com.gestion.exception.FichierStockageException;
import com.gestion.service.DocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

//=============================================================================
		            //attributs
//=============================================================================
    private final DocumentService service;

  //=============================================================================
  	            //constructeur
//  	=============================================================================
    public DocumentController(DocumentService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("documents", service.getAll());
        return "documents";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("document", new Document());
        return "document_form";
    }

    @PostMapping
    public String save(@RequestParam("file") MultipartFile file,
                       @RequestParam("nom") String nom,
                       @RequestParam("type") String type) {

    	 
                   String uploadDir = "uploads/";
                   try {
				Files.createDirectories(Paths.get(uploadDir));
			
            String filePath = uploadDir + file.getOriginalFilename();

				Files.write(Paths.get(filePath), file.getBytes());
		
            Document document = new Document();
            document.setNom(nom);
            document.setType(type);
            document.setChemin(filePath);

            service.save(document);
    	    } catch (IOException e) {
    	    	 throw new FichierStockageException("Erreur lors de l’upload du fichier", e);
            }
        return "redirect:/documents";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/documents";
    }
}
