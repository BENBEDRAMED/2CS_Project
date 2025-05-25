package com.example.ms_user.dto;

import com.example.ms_user.model.Locations;
import com.example.ms_user.model.Role;
import com.example.ms_user.model.StatutDemande;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Builder @Data
public class DemandeInscriptionDTO {


    private String nom;
    private String email;
    private String numeroDeTelephone;
    private String motDePasse;
    private Role role;  // Role demandé (LIVREUR ou COMMERCANT)
    private String adresse;
    private String nomBoutique; // Pour commercant uniquement
    private String numRC; // Pour commercant uniquement
    private StatutDemande statut = StatutDemande.EN_ATTENTE; // EN_ATTENTE, ACCEPTEE, REFUSEE
    private LocationDTO location;

}
