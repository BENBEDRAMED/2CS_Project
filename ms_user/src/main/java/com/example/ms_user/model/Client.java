package com.example.ms_user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Client extends Utilisateur {

    private String adresse;
}
