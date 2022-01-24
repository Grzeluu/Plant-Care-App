package com.grzeluu.plantcareapp.ui_temporary.discover;

import com.grzeluu.plantcareapp.model.Plant;
import com.grzeluu.plantcareapp.base.BaseView;

import java.util.List;

public interface DiscoverMvpView extends BaseView {

    void updatePlants(List<Plant> plantList);
}