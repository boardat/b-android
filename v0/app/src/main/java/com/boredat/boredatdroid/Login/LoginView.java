package com.boredat.boredatdroid.Login;

/**
 * Created by Liz on 7/9/2015.
 */
public interface LoginView {
    public void showProgress();

    public String getUsername();
    public String getPassword();

    public void hideProgress();

    public void setUsernameError();

    public void setPasswordError();

    public void setNetworkError(int errorNo, String errorMessage);

    public void navigateToHome();
}
