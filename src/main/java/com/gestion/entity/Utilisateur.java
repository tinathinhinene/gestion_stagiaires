package com.gestion.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

//======================================================================================
	                      //attributs
//======================================================================================

@Entity
@Table(name = "utilisateur")

public class Utilisateur implements UserDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uti_id")
    private Integer id;

    @Column(name = "uti_nom", length = 100, nullable = false, unique = true)
    private String nom;

    @Column(name = "uti_mot_de_passe", length = 255, nullable = false)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(name = "uti_role", nullable = false)
    private RoleUtilisateur role;
    
    @OneToOne(mappedBy = "utilisateur")
    private Formateur formateur;
    

  //======================================================================================
  //constructeurs
  //======================================================================================	

    public Utilisateur() {}

	public Utilisateur(Integer id, String nom, String motDePasse, RoleUtilisateur role, Formateur formateur) {
	this.id = id;
	this.nom = nom;
	this.motDePasse = motDePasse;
	this.role = role;
	this.formateur = formateur;
}
	//======================================================================================
	//getter setter
	//======================================================================================	

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public RoleUtilisateur getRole() {
        return role;
    }

    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }

    public Formateur getFormateur() {
		return formateur;
	}

	public void setFormateur(Formateur formateur) {
		this.formateur = formateur;
	}

	@Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + ", role=" + role + "]";
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}


	@Override
	public String getPassword() {
		
		return motDePasse;
	}

	@Override
	public String getUsername() {
		return nom;
	}

	@Override
	public boolean isAccountNonExpired() { return true; }

	@Override
	public boolean isAccountNonLocked() { return true; }

	@Override
	public boolean isCredentialsNonExpired() { return true; }

	@Override
	public boolean isEnabled() { return true; }
	
}
