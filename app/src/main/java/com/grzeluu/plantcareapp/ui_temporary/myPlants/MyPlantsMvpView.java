package com.grzeluu.plantcareapp.ui_temporary.myPlants;

import com.grzeluu.plantcareapp.model.UserPlant;
import com.grzeluu.plantcareapp.base.BaseView;

import java.util.List;

public interface MyPlantsMvpView extends BaseView {
    void updatePlants(List<UserPlant> plantList);
}
