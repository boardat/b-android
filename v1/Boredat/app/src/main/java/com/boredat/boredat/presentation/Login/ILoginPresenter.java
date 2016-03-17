package com.boredat.boredat.presentation.Login;

/**
 * Created by Liz on 2/9/2016.
 */
public interface ILoginPresenter {
    boolean validate(String userId, String password);
    void login(String userId, String password);
}
