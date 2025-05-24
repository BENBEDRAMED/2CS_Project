package com.example.ms_commande.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité Produit représentant un produit dans le système.
 * Un produit appartient à une catégorie (relation Many-to-One).
 */
@Entity
@Table(name = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {

    /**
     * Identifiant unique du produit, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * Cette référence pointe vers un utilisateur dans le microservice utilisateur (ms-user).
     */
    private Integer idCommercant;

    /**
     * Prix unitaire du produit.
     */
    private Float prix;

    /**
     * Quantité en stock du produit.
     */
    private Integer stock;

    /**
     * Catégorie à laquelle appartient ce produit.
     * La relation Many-to-One signifie que plusieurs produits peuvent appartenir à une même catégorie.
     */
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    /**
     * Ajoute une quantité spécifiée au stock du produit.
     *
     * @param qte La quantité à ajouter au stock
     */
    public void ajouterStock(Integer qte) {
        this.stock += qte;
    }

    /**
     * Réduit le stock du produit si la quantité disponible est suffisante.
     *
     * @param qte La quantité à retirer du stock
     * @return true si l'opération a réussi (stock suffisant), false sinon
     */
    public boolean reduireStock(Integer qte) {
        if (this.stock >= qte) {
            this.stock -= qte;
            return true;
        }
        return false;
    }
}