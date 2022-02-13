package com.grzeluu.plantcareapp.core.myplants;

import com.grzeluu.plantcareapp.base.BasePresenter;
import com.grzeluu.plantcareapp.model.UserPlant;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyPlantsPresenter extends BasePresenter
        implements MyPlantsContract.Presenter, MyPlantsContract.Listener {

    private MyPlantsContract.View myPlantsView;
    private MyPlantsInteractor myPlantsInteractor;

    public MyPlantsPresenter(MyPlantsContract.View myPlantsView) {
        super(myPlantsView);
        this.myPlantsView = myPlantsView;
        myPlantsInteractor = new MyPlantsInteractor(this);
    }

    @Override
    public void getMyPlantsList() {
        myPlantsInteractor.fetchMyPlantList();
    }

    @Override
    public void deletePlant(UserPlant plant) {
        myPlantsInteractor.performDeletePlant(plant);
    }

    @Override
    public void updatePlantNeeds(UserPlant plant, boolean isWatered, boolean isFertilized, boolean isSprayed) {
        myPlantsInteractor.performUpdatePlantNeeds(plant, isWatered, isFertilized, isSprayed);
    }

    @Override
    public void onSuccess(List<UserPlant> plantList) {
        Collections.sort(plantList, new UserPlantsComparator());
        myPlantsView.updateMyPlants(plantList);
    }

    @Override
    public void onFailure(String message) {
        myPlantsView.showMessage(message);
    }

    @Override
    public void onSuccess(int message) {
        myPlantsView.showMessage(message);
    }

    @Override
    public void onSuccess(UserPlant plant) {
        myPlantsView.setNewNotificationForPlant(plant);
    }

    @Override
    public void onPlantDeleted(String id) {
        myPlantsView.plantDeleted(id);
    }

    static class UserPlantsComparator implements Comparator<UserPlant> {
        @Override
        public int compare(UserPlant a, UserPlant b) {
            return Integer.compare(a.getDaysToClosestAction(), b.getDaysToClosestAction());
        }
    }

}
