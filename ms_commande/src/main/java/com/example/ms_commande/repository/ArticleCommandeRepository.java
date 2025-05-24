package com.example.ms_commande.repository;

import com.example.ms_commande.model.ArticleCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleCommandeRepository extends JpaRepository<ArticleCommande, Integer> {
    List<ArticleCommande> findByCommandeId(Integer commandeId);
}

