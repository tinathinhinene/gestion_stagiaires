package com.gestion.security;

import com.gestion.entity.Utilisateur;
import com.gestion.entity.RoleUtilisateur;
import com.gestion.repository.UtilisateurRepository;
import com.gestion.security.dto.AuthRequest;
import com.gestion.security.dto.AuthResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UtilisateurRepository utilisateurRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================================
    //                        LOGIN
    // ============================================================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getNom(),        // nom français
                            request.getMotDePasse()  // motDePasse français
                    )
            );

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Identifiants incorrects");
        }
    }

    // ============================================================
    //        REGISTER — Version Française (nom + motDePasse + role)
    // ============================================================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> data) {

        String nom = (String) data.get("nom");
        String motDePasse = (String) data.get("motDePasse");
        String roleStr = (String) data.get("role");

        // Vérifier si nom existe
        if (utilisateurRepository.findByNom(nom).isPresent()) {
            return ResponseEntity.badRequest().body("Nom d'utilisateur déjà existant");
        }

        // Création de l'utilisateur
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setMotDePasse(passwordEncoder.encode(motDePasse));

        // Convertit la string en enum
        if (roleStr != null) {
            u.setRole(RoleUtilisateur.valueOf(roleStr.toLowerCase()));
        } else {
            u.setRole(RoleUtilisateur.formateur); // rôle par défaut si tu veux
        }

        utilisateurRepository.save(u);

        return ResponseEntity.ok("Utilisateur créé avec succès");
    }
    
}
