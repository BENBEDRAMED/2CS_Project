package com.example.ms_user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Livreur extends Utilisateur {

    private String vehicule;

    private float noteMoyenne;

    private boolean disponibilite;
}
