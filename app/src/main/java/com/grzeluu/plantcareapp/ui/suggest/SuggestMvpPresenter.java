package com.grzeluu.plantcareapp.ui.suggest;

import android.net.Uri;

public interface SuggestMvpPresenter {

    void onSuggestNewPlantClick(Uri photo, String commonName, String latinName, String type, String description,
                                int frequencyOfWatering, int frequencyOfFertilization, int frequencyOfSpraying);

}
