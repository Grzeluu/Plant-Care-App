package com.grzeluu.plantcareapp.ui.myPlants;

import com.grzeluu.plantcareapp.model.UserPlant;
import com.grzeluu.plantcareapp.ui.base.BaseMvpView;

import java.util.List;

public interface MyPlantsMvpView extends BaseMvpView {
    void updatePlants(List<UserPlant> plantList);
}
