package com.example.ms_commande.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private String type = "Point"; // Always "Point"
    private List<Double> coordinates;



    public LocationDto(double longitude, double latitude) {
        this.type = "Point";
        this.coordinates = List.of(longitude, latitude);
    }
}