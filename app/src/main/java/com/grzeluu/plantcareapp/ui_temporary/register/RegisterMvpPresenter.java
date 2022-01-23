package com.grzeluu.plantcareapp.ui_temporary.register;

public interface RegisterMvpPresenter {
    void onRegisterClick( String username, String email, String password, String repeatedPassword);

    void onDestroy();
}
