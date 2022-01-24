package com.grzeluu.plantcareapp.ui_temporary.discover;

import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.base.BaseViewContract;

import java.util.List;

public interface DiscoverMvpView extends BaseViewContract {

    void updatePlants(List<Plant> plantList);
}