package com.boredat.boredatdroid.Login;

import com.boredat.boredatdroid.models.Personality;
import com.boredat.boredatdroid.models.User;
import com.boredat.boredatdroid.network.oauth.OauthLoginResponse;
import com.boredat.boredatdroid.network.oauth.OauthResponse;

/**
 * Created by Liz on 7/9/2015.
 */
public interface OnLoginListener {
    public void onTokenReceived(OauthResponse response);

    public void onVerifierReceived(OauthLoginResponse response);

    public void onAccessReceived(OauthResponse response);

    public void onUsernameError();

    public void onPasswordError();

    public void onRequestTokenError();

    public void onAuthenticateError();

    public void onAccessError();

    public void onGetUserError(User response);

    public void onGetUserSuccess(User response);

    public void onGetPersonalityError(Personality response);

    public void onGetPersonalitySuccess(Personality response);

    public void onLoginFinished(String imageUrl);
}
