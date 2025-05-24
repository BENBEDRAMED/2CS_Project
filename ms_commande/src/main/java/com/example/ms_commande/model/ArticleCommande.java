package com.example.ms_commande.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité ArticleCommande représentant un article dans une commande (ligne de commande).
 * Cette entité établit la relation entre une commande et un produit, avec une quantité spécifique.
 */
@Entity
@Table(name = "articles_commande")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCommande {
    
    /**
     * Identifiant unique de l'article de commande, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Quantité du produit commandée.
     */
    private Integer quantite;
    
    /**
     * Prix total pour cet article (quantité * prix unitaire du produit).
     */
    private Float prixTotal;
    
    /**
     * Commande à laquelle appartient cet article.
     * La relation Many-to-One signifie que plusieurs articles peuvent appartenir à une même commande.
     */
    @ManyToOne
    @JoinColumn(name = "commande_id")
    @JsonIgnore
    private Commande commande;
    
    /**
     * Produit commandé.
     * La relation Many-to-One signifie que le même produit peut être commandé dans plusieurs articles.
     */
    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;



    /**
     * Calcule le prix total de cet article en multipliant la quantité par le prix unitaire du produit.
     * 
     * @return Le prix total calculé
     */
    public Float calculerPrix() {
        if (produit != null && quantite != null) {
            this.prixTotal = produit.getPrix() * quantite;
            return this.prixTotal;
        }
        return 0f;
    }
}

