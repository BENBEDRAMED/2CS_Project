package com.example.ms_commande.client;


import com.example.ms_commande.dto.LivraisonCommandeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-livraison", url = "http://localhost:3000")
public interface LivraisonServiceClient {

    // to create order in ms liv database
    @PostMapping("/api/orders")
    void envoyerCommande(@RequestBody LivraisonCommandeDto dto);
}
