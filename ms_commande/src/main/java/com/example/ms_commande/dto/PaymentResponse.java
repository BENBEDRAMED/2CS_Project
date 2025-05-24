// PaymentResponse.java
package com.example.ms_commande.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private String checkout_url;
    private String status; // "pending", "paid", "failed"
}