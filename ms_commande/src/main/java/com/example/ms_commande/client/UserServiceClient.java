package com.example.ms_commande.client;

import com.example.ms_commande.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Client Feign pour communiquer avec le microservice utilisateur (ms-user).
 * Ce client permet d'appeler les API du microservice utilisateur de manière déclarative.
 * 
 * L'annotation @FeignClient spécifie le nom du service et son URL.
 * L'URL est configurée dans le fichier application.properties.
 */
@FeignClient(name = "ms-user")
public interface UserServiceClient {
    
    /**
     * Récupère un utilisateur par son ID.
     * 
     * @param id L'ID de l'utilisateur à récupérer
     * @return L'utilisateur correspondant à l'ID
     */
    @GetMapping("/api/utilisateurs/{id}")
    UserDTO getUserById(@PathVariable("id") Integer id);
    
    /**
     * Récupère tous les utilisateurs ayant le rôle de commerçant.
     * 
     * @return La liste des commerçants
     */
    @GetMapping("/api/utilisateurs/commercants")
    Iterable<UserDTO> getAllCommercants();
    
    /**
     * Vérifie si un commerçant existe avec l'ID spécifié.
     * 
     * @param id L'ID du commerçant à vérifier
     * @return true si le commerçant existe, false sinon
     */
    @GetMapping("/api/utilisateurs/commercant/{id}/exists")
    boolean commercantExists(@PathVariable("id") Integer id);
}

