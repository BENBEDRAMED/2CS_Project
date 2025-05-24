package com.example.ms_commande.repository;

import com.example.ms_commande.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Integer> {
    List<Commande> findByIdClient(Integer idClient);
    List<Commande> findByStatut(String statut);
}

