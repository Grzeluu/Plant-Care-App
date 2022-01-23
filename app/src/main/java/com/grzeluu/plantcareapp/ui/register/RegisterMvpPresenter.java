package com.grzeluu.plantcareapp.ui.register;

public interface RegisterMvpPresenter {
    void onRegisterClick( String username, String email, String password, String repeatedPassword);

    void onDestroy();
}
