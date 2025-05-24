package com.example.ms_commande.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité Categorie représentant une catégorie de produits dans le système.
 * Une catégorie peut contenir plusieurs produits (relation One-to-Many).
 */
@Entity
@Table(name = "categories")
@Data // Génère automatiquement getters, setters, equals, hashCode et toString
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
public class Categorie {

    /**
     * Identifiant unique de la catégorie, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nom de la catégorie.
     */
    private String nom;

    /**
     * Liste des produits appartenant à cette catégorie.
     * La relation est mappée par l'attribut "categorie" dans l'entité Produit.
     * CascadeType.ALL signifie que toutes les opérations (persist, merge, remove, etc.)
     * seront propagées aux produits associés.
     */
    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL)

    private List<Produit> produits = new ArrayList<>();

    /**
     * Ajoute un produit à cette catégorie et établit la relation bidirectionnelle.
     *
     * @param produit Le produit à ajouter à la catégorie
     */
    public void ajouterProduit(Produit produit) {
        produits.add(produit);
        produit.setCategorie(this); // Établit la relation bidirectionnelle
    }

    /**
     * Supprime un produit de cette catégorie en utilisant son ID.
     *
     * @param produitId L'ID du produit à supprimer
     */
    public void supprimerProduit(Integer produitId) {
        produits.removeIf(produit -> produit.getId().equals(produitId));
    }
}

