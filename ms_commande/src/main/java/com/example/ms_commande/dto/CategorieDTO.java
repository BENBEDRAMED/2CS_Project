package com.example.ms_commande.dto;

import com.example.ms_commande.model.Categorie;
import lombok.Data;

@Data
public class CategorieDTO {
    private Integer id;
    private String nom;

    public static CategorieDTO fromCategorie(Categorie categorie) {
        CategorieDTO dto = new CategorieDTO();
        dto.setId(categorie.getId());
        dto.setNom(categorie.getNom());
        return dto;
    }
}