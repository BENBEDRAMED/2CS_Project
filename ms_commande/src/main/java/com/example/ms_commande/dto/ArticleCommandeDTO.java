package com.example.ms_commande.dto;

import lombok.Data;

@Data
public class ArticleCommandeDTO {
    private Integer id;
    private Integer quantite;
    private Float prixTotal;
    private ProduitDTO produit;
}