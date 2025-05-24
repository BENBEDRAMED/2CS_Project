package com.example.ms_commande.service;

import com.example.ms_commande.client.UserServiceClient;
import com.example.ms_commande.dto.ProduitDTO;
import com.example.ms_commande.dto.UserDTO;
import com.example.ms_commande.model.Produit;
import com.example.ms_commande.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public Optional<Produit> getProduitById(Integer id) {
        return produitRepository.findById(id);
    }

    public List<Produit> getProduitsByCommercant(Integer idCommercant) {
        return produitRepository.findByIdCommercant(idCommercant);
    }

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Integer id, Produit produit) {
        Optional<Produit> existingProduit = produitRepository.findById(id);
        if (existingProduit.isPresent()) {
            Produit updatedProduit = existingProduit.get();
            updatedProduit.setNom(produit.getNom());
            updatedProduit.setDescription(produit.getDescription());
            updatedProduit.setPrix(produit.getPrix());
            updatedProduit.setStock(produit.getStock());
            updatedProduit.setIdCommercant(produit.getIdCommercant());
            updatedProduit.setCategorie(produit.getCategorie());
            return produitRepository.save(updatedProduit);
        } else {
            throw new RuntimeException("Produit not found with id: " + id);
        }
    }

    public void deleteProduit(Integer id) {
        produitRepository.deleteById(id);
    }

    public Produit updateStock(Integer id, String action, Integer quantite) {
        Optional<Produit> produitOpt = produitRepository.findById(id);
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get();
            if ("ajouter".equalsIgnoreCase(action)) {
                produit.ajouterStock(quantite);
            } else if ("reduire".equalsIgnoreCase(action)) {
                if (!produit.reduireStock(quantite)) {
                    throw new RuntimeException("Insufficient stock for product: " + id);
                }
            }
            return produitRepository.save(produit);
        } else {
            throw new RuntimeException("Produit not found with id: " + id);
        }
    }

    // get all product with commercant name
    public List<ProduitDTO> getAllProduitDTOs() {
        return produitRepository.findAll().stream().map(produit -> {
            UserDTO commercant = null;
            try {
                if (produit.getIdCommercant() != null) {
                    commercant = userServiceClient.getUserById(produit.getIdCommercant());

                    // 👇 Debug log
//                    System.out.println("Produit ID: " + produit.getId());
//                    System.out.println("Commercant fetched: " + commercant);
                }
            } catch (Exception e) {
                System.err.println("Erreur récupération commerçant via Feign : " + e.getMessage());
            }

            return ProduitDTO.fromProduit(produit, commercant);
        }).collect(Collectors.toList());
    }
}