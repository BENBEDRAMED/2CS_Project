package com.example.ms_commande.model;

public enum PaymentStatus {
    UNPAID,    // Initial state
    PENDING,   // Payment initiated
    PAID,      // Payment successful
    FAILED     // Payment failed
}

