package com.example.ms_commande.dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String commande_id;
    private double amount;
}