package com.grzeluu.plantcareapp.ui_temporary.add;

import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.ui_temporary.base.BaseMvpView;

public interface AddPlantMvpView extends BaseMvpView {
    void setPlantCommonName(String commonName);

    void setPlantLatinName(String latinName);

    void setPlantType(String plantType);

    void setPlantPhoto(String uri);

    void setWateringFrequency(long wateringFrequency);

    void setFertilizingFrequency(long fertilizingFrequency);

    void setSprayingFrequency(long sprayingFrequency);

    void setNameError(String error);

    void setPlant(Plant plant);
}