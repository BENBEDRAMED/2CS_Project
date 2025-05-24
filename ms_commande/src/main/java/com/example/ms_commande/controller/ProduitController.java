package com.example.ms_commande.controller;

import com.example.ms_commande.dto.ProduitDTO;
import com.example.ms_commande.dto.UserDTO;
import com.example.ms_commande.model.Produit;
import com.example.ms_commande.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;
//
//    @GetMapping
//    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
//        List<ProduitDTO> produits = produitService.getAllProduits().stream()
//                .map(produit -> ProduitDTO.fromProduit(produit, null))
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(produits);
//    }
    // for get all product
    @GetMapping
    public List<ProduitDTO> getAllProduitDTOs() {
        return produitService.getAllProduitDTOs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Integer id) {
        return produitService.getProduitById(id)
                .map(produit -> ResponseEntity.ok(ProduitDTO.fromProduit(produit, null)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/commercant/{idCommercant}")
    public ResponseEntity<List<ProduitDTO>> getProduitsByCommercant(@PathVariable Integer idCommercant) {
        List<ProduitDTO> produits = produitService.getProduitsByCommercant(idCommercant).stream()
                .map(produit -> ProduitDTO.fromProduit(produit, null))
                .collect(Collectors.toList());
        return ResponseEntity.ok(produits);
    }

    @PostMapping("")
    public ResponseEntity<ProduitDTO> createProduit(@RequestBody Produit produit) {
        Produit savedProduit = produitService.createProduit(produit);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProduitDTO.fromProduit(savedProduit, null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDTO> updateProduit(@PathVariable Integer id, @RequestBody Produit produit) {
        Produit updatedProduit = produitService.updateProduit(id, produit);
        return ResponseEntity.ok(ProduitDTO.fromProduit(updatedProduit, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Integer id) {
        produitService.deleteProduit(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/stock/{action}")
    public ResponseEntity<ProduitDTO> updateStock(
            @PathVariable Integer id,
            @PathVariable String action,
            @RequestParam Integer quantite) {
        Produit updatedProduit = produitService.updateStock(id, action, quantite);
        return ResponseEntity.ok(ProduitDTO.fromProduit(updatedProduit, null));
    }

}