package com.example.ms_user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Commercant extends Utilisateur {

    private String nomBoutique;

    private String adresse;

    private String NumRC;

}
