package com.grzeluu.plantcareapp.ui_temporary.discover;

import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.base.BaseMvpView;

import java.util.List;

public interface DiscoverMvpView extends BaseMvpView {

    void updatePlants(List<Plant> plantList);
}