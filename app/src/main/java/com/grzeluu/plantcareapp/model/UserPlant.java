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

    String type;

    String image;

    public UserPlant() {
    }

    public UserPlant(String id, String name, String commonName, String latinName, long wateringFrequency, long fertilizingFrequency, long sprayingFrequency, String lastFertilizing, String lastSpraying, String lastWatering, String type, String image) {
        this.id = id;
        this.name = name;
        this.commonName = commonName;
        this.latinName = latinName;
        this.wateringFrequency = wateringFrequency;
        this.fertilizingFrequency = fertilizingFrequency;
        this.sprayingFrequency = sprayingFrequency;
        this.lastFertilizing = lastFertilizing;
        this.lastSpraying = lastSpraying;
        this.lastWatering = lastWatering;
        this.type = type;
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

    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
