package com.example.ms_user.proxy;

import com.example.ms_user.dto.LivreurDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url="http://localhost:3000", name="ms-livraison")
public interface LivreurLiv {
    // create livreur
    @PostMapping("/api/livreurs")
    void createLivreur(@RequestBody LivreurDto livreurDto);

    // update livreur
    @PutMapping("/api/livreurs/{id}")
    void updateLivreur(@PathVariable("id") int id, @RequestBody LivreurDto livreurDTO);

    // delete livreur
    @DeleteMapping("/api/livreurs/{id}")
    void deleteLivreur(@PathVariable("id") int id);


}
