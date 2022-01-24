package com.grzeluu.plantcareapp.ui_temporary.myPlants;

import com.grzeluu.plantcareapp.model.UserPlant;
import com.grzeluu.plantcareapp.base.BaseViewContract;

import java.util.List;

public interface MyPlantsMvpView extends BaseViewContract {
    void updatePlants(List<UserPlant> plantList);
}
