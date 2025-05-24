package com.example.ms_user.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Locations {

    private String type = "Point"; // Always "Point"

    private Double longitude;
    private Double latitude;

    public Locations() {
        this.type = "Point";
    }

    public Locations(Double longitude, Double latitude) {
        this.type = "Point";
        this.longitude = longitude;
        this.latitude = latitude;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
