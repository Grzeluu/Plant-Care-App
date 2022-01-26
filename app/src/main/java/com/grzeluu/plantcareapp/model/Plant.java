package com.grzeluu.plantcareapp.model;

import java.io.Serializable;

public class Plant implements Serializable {

    String id;

    String commonName;
    String latinName;
    String description;
    String type;

    long wateringFrequency;
    long fertilizingFrequency;
    long sprayingFrequency;

    String image;

    boolean isVerified;

    public Plant() {
    }

    public Plant(String commonName,
                 String latinName,
                 String description,
                 String type,
                 long wateringFrequency,
                 long fertilizingFrequency,
                 long sprayingFrequency,
                 String photoUri,
                 boolean isVerified) {
        this.commonName = commonName;
        this.latinName = latinName;
        this.type = type;
        this.description = description;
        this.wateringFrequency = wateringFrequency;
        this.fertilizingFrequency = fertilizingFrequency;
        this.sprayingFrequency = sprayingFrequency;
        this.image = photoUri;
        this.isVerified = isVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(long wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public long getFertilizingFrequency() {
        return fertilizingFrequency;
    }

    public void setFertilizingFrequency(long fertilizingFrequency) {
        this.fertilizingFrequency = fertilizingFrequency;
    }

    public long getSprayingFrequency() {
        return sprayingFrequency;
    }

    public void setSprayingFrequency(long sprayingFrequency) {
        this.sprayingFrequency = sprayingFrequency;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}