package com.grzeluu.plantcareapp.core.discover;

import com.grzeluu.plantcareapp.base.BaseListenerContract;
import com.grzeluu.plantcareapp.base.BasePresenterContract;
import com.grzeluu.plantcareapp.base.BaseViewContract;
import com.grzeluu.plantcareapp.model.Plant;

import java.util.List;

public interface DiscoverContract {
    interface View extends BaseViewContract {
        void setDiscoverPlantList(List<Plant> plantList);
    }

    interface Presenter extends BasePresenterContract {
        void getAllPlants();

        void getMatchingPlants(String regex);
    }

    interface Interactor {
        void performGetAllPlants();

        void performGetMatchingPlants(String regex);
    }

    interface Listener extends BaseListenerContract {
        void onSuccess(List<Plant> plantList);

        void onFailure(String message);
    }
}
