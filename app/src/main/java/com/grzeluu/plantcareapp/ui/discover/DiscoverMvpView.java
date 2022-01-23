package com.grzeluu.plantcareapp.ui.discover;

import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.ui.base.BaseMvpView;

import java.util.List;

public interface DiscoverMvpView extends BaseMvpView {

    void updatePlants(List<Plant> plantList);
}