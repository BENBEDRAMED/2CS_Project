package com.example.ms_commande.service;

import com.example.ms_commande.client.LivraisonServiceClient;
import com.example.ms_commande.dto.*;
import com.example.ms_commande.model.ArticleCommande;
import com.example.ms_commande.model.Commande;
import com.example.ms_commande.model.Produit;
import com.example.ms_commande.model.PaymentStatus;
import com.example.ms_commande.repository.ArticleCommandeRepository;
import com.example.ms_commande.repository.CommandeRepository;
import com.example.ms_commande.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private ArticleCommandeRepository articleCommandeRepository;

    @Autowired
    private ProduitRepository produitRepository;

    public List<CommandeDTO> getAllCommandes() {
        return commandeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<Commande> getCommandeByIdForUpdate(Integer id) {
        return commandeRepository.findById(id);
    }

    public Optional<CommandeDTO> getCommandeById(Integer id) {
        return commandeRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<CommandeDTO> getCommandesByClient(Integer idClient) {
        return commandeRepository.findByIdClient(idClient).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<CommandeDTO> getCommandesByStatut(String statut) {
        return commandeRepository.findByStatut(statut).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    // to create orders
    @Transactional
    public Commande creerCommande(Integer idClient, List<ArticleCommande> articles) {
        Commande commande = new Commande();
        commande.setDate(LocalDateTime.now());
        commande.setIdClient(idClient);
        commande.setStatut("EN_ATTENTE");

        Commande savedCommande = commandeRepository.save(commande);

        for (ArticleCommande article : articles) {
            Optional<Produit> produitOpt = produitRepository.findById(article.getProduit().getId());
            if (produitOpt.isPresent()) {
                article.setProduit(produitOpt.get());
                savedCommande.addArticle(article);
            } else {
                throw new IllegalArgumentException("Produit with ID " + article.getProduit().getId() + " does not exist.");
            }
        }

        return commandeRepository.save(savedCommande);
    }

    @Autowired
    private LivraisonServiceClient livraisonProxy;

    @Transactional
    public boolean validerCommande(Integer commandeId) {
        Optional<Commande> commandeOpt = commandeRepository.findById(commandeId);
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            boolean result = commande.validerCommande();
            if (result) {
                commandeRepository.save(commande);

                // Prepare DTO
                LivraisonCommandeDto dto = new LivraisonCommandeDto();
                dto.setIdCommande(commande.getId());
                dto.setIdClient(commande.getIdClient());
                dto.setQuantite(commande.getArticles().get(0).getQuantite());
                dto.setIdArticle(commande.getArticles().get(0).getId());
                dto.setPrixArticle(commande.getArticles().get(0).getPrixTotal());
                dto.setIdCommercant(commande.getArticles().get(0).getProduit().getIdCommercant());

                LocationDto location = new LocationDto();
                location.setLat(0);  // Replace with real coordinates
                location.setLng(0);
                dto.setLocation(location);

                dto.setLivraisonPayment(0.0);
                // 👉 Debug log before Feign call
                System.out.println("🚀 Sending to ms-livraison: " + dto);
                // Send to ms-livraison
                try {
                    livraisonProxy.envoyerCommande(dto);
                    System.out.println("✅ Commande sent to ms-livraison");
                } catch (Exception e) {
                    System.err.println("❌ Failed to send commande to ms-livraison: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            return result;
        }
        return false;
    }

    @Transactional
    public boolean annulerCommande(Integer commandeId) {
        Optional<Commande> commandeOpt = commandeRepository.findById(commandeId);
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            boolean result = commande.annulerCommande();
            if (result) {
                commandeRepository.save(commande);
            }
            return result;
        }
        return false;
    }

    public Float calculerTotalCommande(Integer commandeId) {
        Optional<Commande> commandeOpt = commandeRepository.findById(commandeId);
        return commandeOpt.map(Commande::calculerTotal).orElse(0f);
    }

    @Transactional
    public void ajouterArticle(Integer commandeId, ArticleCommande article) {
        Optional<Commande> commandeOpt = commandeRepository.findById(commandeId);
        if (commandeOpt.isPresent()) {
            Commande commande = commandeOpt.get();
            Optional<Produit> produitOpt = produitRepository.findById(article.getProduit().getId());
            if (produitOpt.isPresent()) {
                article.setProduit(produitOpt.get());
                commande.addArticle(article);
                commandeRepository.save(commande);
            }
        }
    }

    @Transactional
    public CommandeDTO updatePaymentStatus(Integer id, PaymentStatus paymentStatus) {
        Commande commande = getCommandeByIdForUpdate(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        commande.setPaymentStatus(paymentStatus);
        commandeRepository.save(commande);
        return convertToDTO(commande);
    }

    private CommandeDTO convertToDTO(Commande commande) {
        CommandeDTO dto = new CommandeDTO();
        dto.setId(commande.getId());
        dto.setDate(commande.getDate());
        dto.setIdClient(commande.getIdClient());
        dto.setStatut(commande.getStatut());
        dto.setArticles(commande.getArticles().stream()
                .map(this::convertToArticleDTO)
                .collect(Collectors.toList()));
        dto.setMontantTotal(commande.getMontantTotal());
        dto.setPaymentStatus(commande.getPaymentStatus());
        return dto;
    }

    private ArticleCommandeDTO convertToArticleDTO(ArticleCommande article) {
        ArticleCommandeDTO dto = new ArticleCommandeDTO();
        dto.setId(article.getId());
        dto.setQuantite(article.getQuantite());
        dto.setPrixTotal(article.getPrixTotal());
        dto.setProduit(ProduitDTO.fromProduit(article.getProduit(), null));
        return dto;
    }
}