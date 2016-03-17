package com.boredat.boredat.presentation.Login;

/**
 * Created by Liz on 12/14/2015.
 */
public interface LoginView {
    String getUserId();
    String getPassword();
    void showMessage(String text);
    void showProgress();
    void hideProgress();
}
