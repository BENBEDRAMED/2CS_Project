package com.example.ms_user.repository;

import com.example.ms_user.model.DemandeInscription;
import com.example.ms_user.model.StatutDemande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeInscriptionRepository extends JpaRepository<DemandeInscription, Integer> {
    List<DemandeInscription> findByStatut(StatutDemande statut);
}
