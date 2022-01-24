package com.grzeluu.plantcareapp.model;

public class UserPlant {

    String id;

    String name;
    String commonName;
    String latinName;

    long wateringFrequency;
    long fertilizingFrequency;
    long sprayingFrequency;

    String lastFertilizing;
    String lastSpraying;
    String lastWatering;

    String image;

    public UserPlant() {
    }

    public UserPlant(
            String id,
            String name,
            String commonName,
            long wateringFrequency,
            long fertilizingFrequency,
            long sprayingFrequency,
            String lastFertilizing,
            String lastSpraying,
            String lastWatering,
            String image) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.wateringFrequency = wateringFrequency;
        this.fertilizingFrequency = fertilizingFrequency;
        this.sprayingFrequency = sprayingFrequency;
        this.lastFertilizing = lastFertilizing;
        this.lastSpraying = lastSpraying;
        this.lastWatering = lastWatering;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
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

    public String getLastFertilizing() {
        return lastFertilizing;
    }

    public void setLastFertilizing(String lastFertilizing) {
        this.lastFertilizing = lastFertilizing;
    }

    public String getLastSpraying() {
        return lastSpraying;
    }

    public void setLastSpraying(String lastSpraying) {
        this.lastSpraying = lastSpraying;
    }

    public String getLastWatering() {
        return lastWatering;
    }

    public void setLastWatering(String lastWatering) {
        this.lastWatering = lastWatering;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
