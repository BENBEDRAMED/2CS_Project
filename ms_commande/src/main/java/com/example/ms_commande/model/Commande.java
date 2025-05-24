package com.example.ms_commande.model;

import com.example.ms_commande.model.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité Commande représentant une commande passée par un client.
 * Une commande contient plusieurs articles (relation One-to-Many avec ArticleCommande).
 */
@Entity
@Table(name = "commandes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    
    /**
     * Identifiant unique de la commande, généré automatiquement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /**
     * Date et heure de création de la commande.
     */
    private LocalDateTime date;
    
    /**
     * Identifiant du client qui a passé la commande.
     * Cette référence pointe vers un utilisateur dans le microservice utilisateur (ms-user).
     */
    private Integer idClient;
    
    /**
     * Statut actuel de la commande (ex: EN_ATTENTE, VALIDEE, ANNULEE, STOCK_INSUFFISANT).
     */
    private String statut;
    
    /**
     * Liste des articles de cette commande.
     * La relation est mappée par l'attribut "commande" dans l'entité ArticleCommande.
     * CascadeType.ALL signifie que toutes les opérations seront propagées aux articles associés.
     * orphanRemoval=true signifie que les articles qui ne sont plus référencés seront supprimés.
     */
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<ArticleCommande> articles = new ArrayList<>();
    
    /**
     * Calcule le montant total de la commande en additionnant les prix totaux de tous les articles.
     * 
     * @return Le montant total de la commande
     */
    public Float calculerTotal() {
        return articles.stream()
                .map(ArticleCommande::getPrixTotal)
                .reduce(0f, Float::sum);
    }

    @Transient // Marks this as a calculated field (not stored in DB)
    private Float montantTotal ;
    public Float getMontantTotal() {
        return calculerTotal();
    }


    /**
     * Valide la commande en vérifiant que tous les produits sont disponibles en stock.
     * Si le stock est suffisant, le statut de la commande est mis à jour et le stock des produits est réduit.
     * 
     * @return true si la commande a été validée avec succès, false sinon
     */
    public boolean validerCommande() {
        // Vérifie que la commande contient au moins un article
        if (articles.isEmpty()) {
            return false;
        }
        
        // Vérifie que tous les produits sont disponibles en stock
        boolean stockSuffisant = true;
        for (ArticleCommande article : articles) {
            Produit produit = article.getProduit();
            if (!produit.reduireStock(article.getQuantite())) {
                stockSuffisant = false;
                break;
            }
        }
        
        // Met à jour le statut de la commande en fonction de la disponibilité du stock
        if (stockSuffisant) {
            this.statut = "VALIDEE";
            return true;
        } else {
            this.statut = "STOCK_INSUFFISANT";
            return false;
        }
    }
    
    /**
     * Annule la commande et remet les produits en stock si la commande était validée.
     * 
     * @return true si la commande a été annulée avec succès
     */
    public boolean annulerCommande() {
        // Si la commande était validée, on remet les produits en stock
        if ("VALIDEE".equals(this.statut)) {
            for (ArticleCommande article : articles) {
                Produit produit = article.getProduit();
                produit.ajouterStock(article.getQuantite());
            }
        }
        
        // Met à jour le statut de la commande
        this.statut = "ANNULEE";
        return true;
    }
    
    /**
     * Ajoute un article à la commande et établit la relation bidirectionnelle.
     * Calcule également le prix total de l'article.
     * 
     * @param article L'article à ajouter à la commande
     */
    public void addArticle(ArticleCommande article) {
        articles.add(article);
        article.setCommande(this); // Établit la relation bidirectionnelle
        article.calculerPrix(); // Calcule le prix total de l'article
    }
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID; // New field

    public void setPaymentStatus(com.example.ms_commande.model.PaymentStatus paymentStatus) {

    }
}

