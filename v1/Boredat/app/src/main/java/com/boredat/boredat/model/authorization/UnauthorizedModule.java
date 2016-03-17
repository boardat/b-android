package com.boredat.boredat.model.authorization;

import android.app.Application;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.boredat.boredat.presentation.Login.LoginListener;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Liz on 2/17/2016.
 */
@Module
public class UnauthorizedModule {
    LoginListener mListener;

    // Constructor needs one parameter to instantiate
    public UnauthorizedModule(LoginListener listener) {
        mListener = listener;
    }

    @Provides @UnauthorizedScope
    RequestQueue provideRequestQueue(Application application) {
        return Volley.newRequestQueue(application);
    }

    @Provides @UnauthorizedScope
    Authenticator provideAuthenticator(RequestQueue requestQueue) {
        return new Authenticator(requestQueue, mListener);
    }

    @Provides @UnauthorizedScope
    SavedAccountsManager provideAccountsManager(SharedPreferences prefs, Gson gson) {
        return new SavedAccountsManager(prefs, gson);
    }
}
