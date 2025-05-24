package com.example.ms_user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DemandeInscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String email;
    private String numeroDeTelephone;
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    private Role role;  // Role demandé (LIVREUR ou COMMERCANT)

    private String adresse;
    private String nomBoutique; // Pour commercant uniquement
    private String numRC; // Pour commercant uniquement

    @Enumerated(EnumType.STRING)
    private StatutDemande statut = StatutDemande.EN_ATTENTE; // EN_ATTENTE, ACCEPTEE, REFUSEE
}
