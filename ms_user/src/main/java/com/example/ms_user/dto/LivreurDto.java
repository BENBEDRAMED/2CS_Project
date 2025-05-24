package com.example.ms_user.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LivreurDto {
    private int id;
    private String nom;
    private String email;
    private String numeroDeTelephone;
    private boolean disponibilite;
}
