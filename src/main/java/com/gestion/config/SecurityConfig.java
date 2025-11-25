package com.gestion.config;

import com.gestion.security.CustomUserDetailsService;
import com.gestion.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,
                          CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // 1) Stateless + CORS + désactiver CSRF
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 2) Autorisations
            .authorizeHttpRequests(auth -> auth

                // --- PUBLIC ---
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Swagger (si tu l'utilises)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                .requestMatchers("/api/stagiaires/**").hasAnyRole("admin", "formateur")


             // --- PROJETS ---
             .requestMatchers("/api/projets/**").hasAnyRole("admin", "formateur")

             // --- STAGES ---
             .requestMatchers("/api/stages/**").hasAnyRole("admin", "formateur")

             // --- REUNIONS ---
             .requestMatchers("/api/reunions/**")
                 .hasAnyRole("admin", "formateur")

             // --- CLASSES ---
             .requestMatchers("/api/classes/**").hasAnyRole("admin", "formateur")

             // --- CENTRES ---
             .requestMatchers("/api/centres/**").hasAnyRole("admin", "formateur")

             // --- COMMENTAIRES ---
             .requestMatchers("/api/commentaires/**").hasAnyRole("admin", "formateur")

             // --- DOCUMENTS ---
             .requestMatchers("/api/documents/**").hasAnyRole("admin", "formateur")
                // --- TOUT LE RESTE = AUTH REQUISE ---
                .anyRequest().authenticated()
            )

            // 3) Intégrer ton UserDetailsService
            .userDetailsService(userDetailsService)

            // 4) Ajouter le filtre JWT avant UsernamePasswordAuthenticationFilter
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Fournit l'AuthenticationManager (utilisé dans AuthController)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
    
    
    

}
