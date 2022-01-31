package com.grzeluu.plantcareapp.model;

import static com.grzeluu.plantcareapp.utils.ProgressUtils.daysFromLastAction;
import static com.grzeluu.plantcareapp.utils.TimeUtils.DATE_FORMAT;

import java.io.Serializable;
import java.text.ParseException;

public class UserPlant implements Serializable {

    String id;

    String name;

    int wateringFrequency;
    int fertilizingFrequency;
    int sprayingFrequency;

    String lastFertilizing;
    String lastSpraying;
    String lastWatering;

    String image;

    public UserPlant() {
    }

    public UserPlant(
            String id,
            String name,
            int wateringFrequency,
            int fertilizingFrequency,
            int sprayingFrequency,
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

    public int getDaysToClosestAction() {
        int wDays = this.wateringFrequency;
        int fDays = this.fertilizingFrequency;
        int sDays = this.sprayingFrequency;

        try {
            int daysFromWatering = daysFromLastAction(DATE_FORMAT.parse(lastWatering));
            int daysFromFertilizing = daysFromLastAction(DATE_FORMAT.parse(lastFertilizing));
            int daysFromSpraying = daysFromLastAction(DATE_FORMAT.parse(lastSpraying));

            wDays = (wDays == 0) ? Integer.MAX_VALUE : wateringFrequency - daysFromWatering;
            fDays = (fDays == 0) ? Integer.MAX_VALUE : fertilizingFrequency - daysFromFertilizing;
            sDays = (sDays == 0) ? Integer.MAX_VALUE : sprayingFrequency - daysFromSpraying;

            return Math.min(Math.min(wDays, fDays), sDays);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Boolean isCareNeeded() {
        try {
            int daysFromWatering = daysFromLastAction(DATE_FORMAT.parse(lastWatering));
            int daysFromFertilizing = daysFromLastAction(DATE_FORMAT.parse(lastFertilizing));
            int daysFromSpraying = daysFromLastAction(DATE_FORMAT.parse(lastSpraying));

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

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public int getFertilizingFrequency() {
        return fertilizingFrequency;
    }

    public void setFertilizingFrequency(int fertilizingFrequency) {
        this.fertilizingFrequency = fertilizingFrequency;
    }

    public int getSprayingFrequency() {
        return sprayingFrequency;
    }

    public void setSprayingFrequency(int sprayingFrequency) {
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
