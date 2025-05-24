package com.example.ms_commande.controller;

import com.example.ms_commande.dto.CategorieDTO;
import com.example.ms_commande.model.Categorie;
import com.example.ms_commande.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins= "*")
@RestController
@RequestMapping("/api/categories")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategories() {
        List<CategorieDTO> categories = categorieService.getAllCategories().stream()
                .map(CategorieDTO::fromCategorie)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategorieDTO> getCategorieById(@PathVariable Integer id) {
        return categorieService.getCategorieById(id)
                .map(categorie -> ResponseEntity.ok(CategorieDTO.fromCategorie(categorie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategorieDTO> createCategorie(@RequestBody Categorie categorie) {
        Categorie savedCategorie = categorieService.createCategorie(categorie);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CategorieDTO.fromCategorie(savedCategorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategorieDTO> updateCategorie(@PathVariable Integer id, @RequestBody Categorie categorie) {
        Categorie updatedCategorie = categorieService.updateCategorie(id, categorie);
        return ResponseEntity.ok(CategorieDTO.fromCategorie(updatedCategorie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Integer id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.ok().build();
    }
}