package com.grzeluu.plantcareapp.ui_temporary.check;

import com.grzeluu.plantcareapp.base.BaseViewContract;

import java.util.List;

public interface CheckPlantMvpView extends BaseViewContract {
    void setPlantName(String commonName);

    void setPlantLatinName(String latinName);

    void setPlantDescription(String description);

    void setWateringFrequency(long wateringFrequency);

    void setFertilizingFrequency(long fertilizingFrequency);

    void setSprayingFrequency(long sprayingFrequency);

    void setPlantPhoto(String uri);

    void setPlantType(String Type);


    void updateAdvices(List adviceList);

    void setPostError(String s);
}
