package com.boredat.boredatdroid.Login;

import android.content.Context;
import android.util.Log;

import com.boredat.boredatdroid.models.Personality;
import com.boredat.boredatdroid.models.User;
import com.boredat.boredatdroid.network.UserSessionManager;
import com.boredat.boredatdroid.network.oauth.OauthLoginResponse;
import com.boredat.boredatdroid.network.oauth.OauthResponse;
import com.boredat.boredatdroid.util.BoredatUtils;

/**
 * Created by Liz on 7/12/2015.
 */
public class LoginPresenterImpl implements LoginPresenter, OnLoginListener {
    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;
    private Context mCtx;
    private static String mTokenSecret;

    // User Session Manager Class
    UserSessionManager sessionManager;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        mLoginView = loginView;
        mLoginInteractor = new LoginInteractorImpl();
        mCtx = context;
    }

    @Override
    public void validateCredentials(UserSessionManager session) {
        this.sessionManager = session;
        mLoginView.showProgress();
        mLoginInteractor.requestToken(mCtx,this);
    }

    @Override
    public void onTokenReceived(OauthResponse response) {
        Log.d("", "Presenter => onTokenReceived");
        mTokenSecret = response.oauth_token_secret;
        mLoginInteractor.login(mLoginView.getUsername(),mLoginView.getPassword(),mCtx,response,this);
    }

    @Override
    public void onVerifierReceived(OauthLoginResponse response) {
        Log.d("", "Presenter => onVerfierReceived");
        mLoginInteractor.requestAccess(mCtx,response,mTokenSecret,this);
    }

    @Override
    public void onAccessReceived(OauthResponse response) {
        Log.d("", "Presenter => onAccessReceived");

        sessionManager.createUserLoginSession(response.oauth_token, response.oauth_token_secret);
        mLoginInteractor.getUserInfo(mCtx,this, sessionManager);
    }

    @Override
    public void onUsernameError() {
        mLoginView.setUsernameError();
        mLoginView.hideProgress();
    }

    @Override
    public void onPasswordError() {
        mLoginView.setPasswordError();
        mLoginView.hideProgress();
    }

    @Override
    public void onRequestTokenError() {
        mLoginView.setNetworkError(0, "request token error");
        mLoginView.hideProgress();
    }

    @Override
    public void onAuthenticateError() {
        mLoginView.setNetworkError(0, "authenticate error");
        mLoginView.hideProgress();
    }

    @Override
    public void onAccessError() {
        mLoginView.setNetworkError(0, "access token error");
        mLoginView.hideProgress();
    }

    @Override
    public void onGetUserError(User response) {
        mLoginView.setNetworkError(response.getError().getErrorNo(),response.getError().getMessage());
    }

    @Override
    public void onGetUserSuccess(User response) {
        Log.d("", "Presenter => onGetUserSuccess");

        sessionManager.setSessionDetails(BoredatUtils.createLocalEndpoint(response.networkShortname),
                BoredatUtils.createLocalUrl(response.networkShortname),
                response.networkName, response.networkShortname,
                response.personalityId, response.personalityName);

        if (response.personalityId != null) {
            // Personality image url
//            mLoginInteractor.getPersonalityImage(response.personalityId,mCtx,this,sessionManager);
            this.onLoginFinished(BoredatUtils.createAnonUserImageUrl(response.networkShortname));
        } else {
            // Anon user image url
            this.onLoginFinished(BoredatUtils.createAnonUserImageUrl(response.networkShortname));
        }

    }

    @Override
    public void onGetPersonalityError(Personality response) {
        mLoginView.setNetworkError(response.getError().getErrorNo(),response.getError().getMessage());
        mLoginView.hideProgress();
    }

    @Override
    public void onGetPersonalitySuccess(Personality response) {
        this.onLoginFinished(BoredatUtils.createUserPersonalityImageUrl(sessionManager,response.image));
    }

    @Override
    public void onLoginFinished(String imageUrl) {
        Log.d("", "Presenter => onLoginFinished");
        Log.d("", "image url => => " + imageUrl);

        sessionManager.setUserImageUrl(imageUrl);
        mLoginView.navigateToHome();
    }
}
