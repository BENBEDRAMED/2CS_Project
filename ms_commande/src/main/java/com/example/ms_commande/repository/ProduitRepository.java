package com.example.ms_commande.repository;

import com.example.ms_commande.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {
    List<Produit> findByCategorieId(Integer categorieId);
    List<Produit> findByIdCommercant(Integer idCommercant);
}

