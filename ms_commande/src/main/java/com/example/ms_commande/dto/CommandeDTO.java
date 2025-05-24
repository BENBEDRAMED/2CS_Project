package com.example.ms_commande.dto;

import com.example.ms_commande.model.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommandeDTO {
    private Integer id;
    private LocalDateTime date;
    private Integer idClient;
    private String statut;
    private List<ArticleCommandeDTO> articles;
    private Float montantTotal;
    private PaymentStatus paymentStatus;
}