package com.example.ms_user.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@Embeddable
public class Locations {

    private String type = "Point"; // Always "Point"

    private List<Double> coordinates; // [longitude, latitude]

}
