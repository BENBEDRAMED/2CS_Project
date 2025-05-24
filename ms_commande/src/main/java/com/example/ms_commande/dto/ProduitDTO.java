package com.example.ms_commande.dto;

import com.example.ms_commande.model.Produit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) pour les produits, enrichi avec les informations du commerçant.
 * Cette classe combine les données du produit avec celles du commerçant pour une représentation complète.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO {
    /**
     * Identifiant unique du produit.
     */
    private Integer id;
    
    /**
     * Nom du produit.
     */
    private String nom;
    
    /**
     * Description détaillée du produit.
     */
    private String description;
    
    /**
     * Identifiant du commerçant qui vend ce produit.
     */
    private Integer id_commercant;
    
    /**
     * Nom complet du commerçant (récupéré du microservice utilisateur).
     */
    private String nom_commercant;
    
    /**
     * Prix unitaire du produit.
     */
    private Float prix;
    
    /**
     * Quantité en stock du produit.
     */
    private Integer stock;
    
    /**
     * Identifiant de la catégorie à laquelle appartient ce produit.
     */
    private Integer categorieId;
    
    /**
     * Nom de la catégorie à laquelle appartient ce produit.
     */
    private String categorieNom;
    
    /**
     * Convertit une entité Produit en ProduitDTO en y ajoutant les informations du commerçant.
     * 
     * @param produit L'entité Produit à convertir
     * @param commercant Les informations du commerçant (peut être null)
     * @return Un ProduitDTO contenant les informations du produit et du commerçant
     */
    public static ProduitDTO fromProduit(Produit produit, UserDTO commercant) {
        ProduitDTO dto = new ProduitDTO();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setDescription(produit.getDescription());
        dto.setId_commercant(produit.getIdCommercant());
        dto.setPrix(produit.getPrix());
        dto.setStock(produit.getStock());
        
        // Ajoute les informations de la catégorie si elle existe
        if (produit.getCategorie() != null) {
            dto.setCategorieId(produit.getCategorie().getId());
            dto.setCategorieNom(produit.getCategorie().getNom());
        }

        
        // Ajoute le nom du commerçant si les informations sont disponibles
        if (commercant != null) {
            dto.setNom_commercant(commercant.getNom() );
        }
        
        return dto;
    }
}

