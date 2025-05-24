package com.example.ms_commande.service;

import com.example.ms_commande.model.Categorie;
import com.example.ms_commande.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieById(Integer id) {
        return categorieRepository.findById(id);
    }

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Categorie updateCategorie(Integer id, Categorie categorie) {
        Optional<Categorie> existingCategorie = categorieRepository.findById(id);
        if (existingCategorie.isPresent()) {
            Categorie updatedCategorie = existingCategorie.get();
            updatedCategorie.setNom(categorie.getNom());
            return categorieRepository.save(updatedCategorie);
        } else {
            throw new RuntimeException("Categorie not found with id: " + id);
        }
    }

    public void deleteCategorie(Integer id) {
        categorieRepository.deleteById(id);
    }
}