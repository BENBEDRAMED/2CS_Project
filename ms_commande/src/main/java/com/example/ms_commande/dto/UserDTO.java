    package com.example.ms_commande.dto;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    /**
     * Data Transfer Object (DTO) représentant un utilisateur du microservice utilisateur.
     * Cette classe est utilisée pour transférer les données d'utilisateur entre les microservices.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDTO {
        /**
         * Identifiant unique de l'utilisateur.
         */
        private Integer id;

        /**
         * Nom d'utilisateur.
         */
        private String username;

        /**
         * Adresse email de l'utilisateur.
         */
        private String email;

        /**
         * Nom de famille de l'utilisateur.
         */
        private String nom;

        /**
         * Prénom de l'utilisateur.
         */
//        private String prenom;

        /**
         * Rôle de l'utilisateur (ex: CLIENT, COMMERCANT, ADMIN).
         */
        private String role;

        // Autres attributs nécessaires peuvent être ajoutés ici
    }

