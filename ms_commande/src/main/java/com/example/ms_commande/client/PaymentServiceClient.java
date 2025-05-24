package com.example.ms_commande.client;

import com.example.ms_commande.dto.PaymentRequest;
import com.example.ms_commande.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-payment", url = "${payment-service.url}")
public interface PaymentServiceClient {

    @PostMapping("/payment/initiate")
    PaymentResponse initiatePayment(@RequestBody PaymentRequest request);

    @GetMapping("/payment/status/{commandeId}")
    PaymentResponse getPaymentStatus(@PathVariable("commandeId") String commandeId);
}