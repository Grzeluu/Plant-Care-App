package com.grzeluu.plantcareapp.core.myplants;

import com.grzeluu.plantcareapp.base.BaseListenerContract;
import com.grzeluu.plantcareapp.base.BasePresenterContract;
import com.grzeluu.plantcareapp.base.BaseViewContract;
import com.grzeluu.plantcareapp.model.UserPlant;

import java.util.List;

public interface MyPlantsContract {
    interface View extends BaseViewContract {
        void updateMyPlants(List<UserPlant> plantList);

    }

    interface Presenter extends BasePresenterContract {
        void getMyPlantsList();

        void updatePlantNeeds(UserPlant plant, boolean isWatered, boolean isFertilized, boolean isSprayed);
    }

    interface Interactor {
        void fetchMyPlantList();

        void performUpdatePlantNeeds(UserPlant plant, boolean isWatered, boolean isFertilized, boolean isSprayed);
    }

    interface Listener extends BaseListenerContract {
        void onSuccess(List<UserPlant> plantList);

        void onFailure(String message);
    }
}
