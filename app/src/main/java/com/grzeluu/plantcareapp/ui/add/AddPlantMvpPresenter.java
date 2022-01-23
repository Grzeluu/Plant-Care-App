package com.grzeluu.plantcareapp.ui.add;

public interface AddPlantMvpPresenter {
    void getPlant(String id);
    void onSuggestNewPlantClick(String name, String imageUri, String commonName, String latinName, String type, long frequencyOfWatering, long frequencyOfFertilization, long frequencyOfSpraying);
}
