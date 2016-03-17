package com.boredat.boredat.presentation.Login;

import com.boredat.boredat.model.api.responses.OauthLoginResponse;
import com.boredat.boredat.model.api.responses.OauthResponse;

/**
 * Created by Liz on 11/30/2015.
 */
public interface OauthListener {
    void onTokenReceived(OauthResponse response);

    void onVerifierReceived(OauthLoginResponse response);

    void onAccessTokenReceived(OauthResponse response);

    // void onUserReceived(UserResponse response);

    // void onError();
}
