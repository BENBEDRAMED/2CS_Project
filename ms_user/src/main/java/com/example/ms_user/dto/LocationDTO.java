package com.example.ms_user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder @Data
public class LocationDTO {
    private List<Double> coordinates; // [longitude, latitude]
}
