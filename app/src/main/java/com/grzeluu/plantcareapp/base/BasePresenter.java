package com.grzeluu.plantcareapp.base;

public class BasePresenter implements BasePresenterContract, BaseListenerContract{

    private BaseViewContract baseView;

    public BasePresenter(BaseViewContract baseView) {
        this.baseView = baseView;
    }

    @Override
    public void onStart() {
        baseView.showLoading();
    }

    @Override
    public void onEnd() {
        baseView.hideLoading();
    }
}
