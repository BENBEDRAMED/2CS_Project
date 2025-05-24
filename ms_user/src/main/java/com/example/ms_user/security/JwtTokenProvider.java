package com.example.ms_user.security;

import com.example.ms_user.model.Utilisateur;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "3d0b9yZaNUU+hK3k1IhP4c2f9Pb7c2wW8jKJ5xPsB9E=\n"; // Remplace par une clé sécurisée
    private final long EXPIRATION_TIME = 86400000; // 1 jour

    public String generateToken(Utilisateur utilisateur) {
        return Jwts.builder()
                .setSubject(utilisateur.getEmail()) // Email comme identifiant
                .setIssuedAt(new Date()) // Date d'émission
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Signature avec clé secrète
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
