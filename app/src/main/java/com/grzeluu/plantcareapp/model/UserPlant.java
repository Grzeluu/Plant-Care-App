package com.grzeluu.plantcareapp.model;

import static com.grzeluu.plantcareapp.utils.Constants.iso_8601_format;
import static com.grzeluu.plantcareapp.utils.DaysUtils.daysFromLastAction;

import java.io.Serializable;
import java.text.ParseException;

public class UserPlant implements Serializable {

    String id;

    String name;

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
            long wateringFrequency,
            long fertilizingFrequency,
            long sprayingFrequency,
            String lastFertilizing,
            String lastSpraying,
            String lastWatering,
            String image) {
        this.id = id;
        this.name = name;
        this.wateringFrequency = wateringFrequency;
        this.fertilizingFrequency = fertilizingFrequency;
        this.sprayingFrequency = sprayingFrequency;
        this.lastFertilizing = lastFertilizing;
        this.lastSpraying = lastSpraying;
        this.lastWatering = lastWatering;
        this.image = image;
    }

    public Boolean isCareNeeded() {
        try {
            long daysFromWatering = daysFromLastAction(iso_8601_format.parse(lastWatering));
            long daysFromFertilizing = daysFromLastAction(iso_8601_format.parse(lastFertilizing));
            long daysFromSpraying = daysFromLastAction(iso_8601_format.parse(lastSpraying));

            if (wateringFrequency != 0 && daysFromWatering >= wateringFrequency)
                return true;
            if (fertilizingFrequency != 0 && daysFromFertilizing >= fertilizingFrequency)
                return true;
            if (sprayingFrequency != 0 && daysFromSpraying >= sprayingFrequency)
                return true;
            return false;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
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
