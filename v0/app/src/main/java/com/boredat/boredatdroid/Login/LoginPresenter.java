package com.boredat.boredatdroid.Login;

import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 7/9/2015.
 */
public interface LoginPresenter {
    public void validateCredentials(UserSessionManager session);

}
