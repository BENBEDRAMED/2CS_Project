package com.example.ms_user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String email;
    private String numeroDeTelephone;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;  // Ajout du rôle
}
