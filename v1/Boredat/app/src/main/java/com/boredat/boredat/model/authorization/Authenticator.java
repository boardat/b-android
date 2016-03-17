package com.boredat.boredat.model.authorization;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boredat.boredat.model.api.requests.OauthLoginAccess;
import com.boredat.boredat.model.api.requests.OauthLoginReq;
import com.boredat.boredat.model.api.requests.OauthReq;
import com.boredat.boredat.model.api.responses.OauthLoginResponse;
import com.boredat.boredat.model.api.responses.OauthResponse;
import com.boredat.boredat.presentation.Login.ILoginPresenter;
import com.boredat.boredat.presentation.Login.LoginListener;
import com.boredat.boredat.presentation.Login.OauthListener;
import com.boredat.boredat.util.Constants;
import com.boredat.boredat.util.oauth.OauthUtils;

/**
 * Created by Liz on 2/17/2016.
 */
public class Authenticator implements ILoginPresenter, OauthListener {
    // Member variables
    private RequestQueue queue;
    private LoginListener listener;
    private String userId;
    private String password;
    private String tokenSecret;

    public Authenticator(RequestQueue queue, LoginListener listener) {
        this.queue = queue;
        this.listener = listener;
    }


    @Override
    public boolean validate(String userId, String password) {
        boolean valid = true;
        // UserId and Password must be at least 5 characters long
        if (userId.length() < 5 || password.length() < 5) {
            valid = false;
        }

        // TODO: figure out what other constraints exist on UserId and Password
        return valid;
    }

    @Override
    public void login(String userId, String password) {
        this.userId = userId;
        this.password = password;
        OauthReq requestToken = new OauthReq(Request.Method.POST, Constants.REQUEST_TOKEN_URL, getVolleyErrorListener(), this);
        queue.add(requestToken);
    }

    @Override
    public void onTokenReceived(OauthResponse response) {
        tokenSecret = response.oauth_token_secret;
        String url = OauthUtils.getLoginRequestURL(userId, password, response.oauth_token);

        OauthLoginReq loginRequest = new OauthLoginReq(Request.Method.GET, url, getVolleyErrorListener(), this);
        queue.add(loginRequest);
    }

    @Override
    public void onVerifierReceived(OauthLoginResponse response) {
        OauthLoginAccess accessRequest = new OauthLoginAccess(Request.Method.POST, Constants.ACCESS_TOKEN_URL, getVolleyErrorListener(), this, response.oauth_token, tokenSecret);
        queue.add(accessRequest);
    }

    @Override
    public void onAccessTokenReceived(OauthResponse response) {
        Account account = new Account(userId, password);
        AccessToken accessToken = new AccessToken(response.oauth_token, response.oauth_token_secret);
        listener.onSuccess(account, accessToken);
    }

    private Response.ErrorListener getVolleyErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Constants.TAG, "Error => " + error.toString());
                listener.onVolleyError(error);
            }
        };
    }
}
