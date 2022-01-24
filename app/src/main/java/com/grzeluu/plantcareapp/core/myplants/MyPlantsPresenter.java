package com.grzeluu.plantcareapp.core.myplants;

import com.grzeluu.plantcareapp.base.BasePresenter;
import com.grzeluu.plantcareapp.model.UserPlant;

import java.util.List;

public class MyPlantsPresenter extends BasePresenter
implements MyPlantsContract.Presenter, MyPlantsContract.Listener {

    private MyPlantsContract.View myPlantsView;
    private MyPlantsInteractor myPlantsInteractor;

    public MyPlantsPresenter( MyPlantsContract.View myPlantsView) {
        super(myPlantsView);
        this.myPlantsView = myPlantsView;
        myPlantsInteractor = new MyPlantsInteractor(this);
    }

    @Override
    public void getMyPlantsList() {
        myPlantsInteractor.fetchMyPlantList();
    }

    @Override
    public void onSuccess(List<UserPlant> plantList) {
        myPlantsView.updateMyPlants(plantList);
    }

    @Override
    public void onFailure(String message) {
        myPlantsView.showMessage(message);
    }
}
