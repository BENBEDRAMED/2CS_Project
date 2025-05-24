package com.example.ms_commande.dto;

import lombok.Data;

@Data
public class LivraisonCommandeDto {
    private int idCommande;
    private int idClient;
    private int idArticle;
    private int quantite;
    private double prixArticle;
    private int idCommercant;
    private LocationDto location;
    private double livraisonPayment = 0.0;
}