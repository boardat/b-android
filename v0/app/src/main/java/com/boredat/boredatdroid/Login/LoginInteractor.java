package com.boredat.boredatdroid.Login;

import android.content.Context;

import com.boredat.boredatdroid.network.UserSessionManager;
import com.boredat.boredatdroid.network.oauth.OauthLoginResponse;
import com.boredat.boredatdroid.network.oauth.OauthResponse;

/**
 * Created by Liz on 7/9/2015.
 */
public interface LoginInteractor {
    public void requestToken(Context mCtx, OnLoginListener listener);

    public void login(String username, String password, Context mCtx, OauthResponse response, OnLoginListener listener);

    public void requestAccess(Context mCtx, OauthLoginResponse response, String mTokenSecret, OnLoginListener listener);

    public void getUserInfo(Context mCtx, OnLoginListener listener, UserSessionManager sessionManager);

    public void getPersonalityImage(String personalityID, Context mCtx, OnLoginListener listener, UserSessionManager sessionManager);
}
