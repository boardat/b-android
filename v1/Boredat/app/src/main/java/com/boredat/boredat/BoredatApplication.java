package com.boredat.boredat;

import android.app.Application;
import android.content.Context;

import com.boredat.boredat.model.api.SessionComponent;
import com.boredat.boredat.model.api.SessionModule;
import com.boredat.boredat.model.authorization.AccessToken;
import com.boredat.boredat.model.authorization.Account;
import com.boredat.boredat.model.authorization.UnauthorizedComponent;
import com.boredat.boredat.model.authorization.UnauthorizedModule;
import com.boredat.boredat.presentation.Login.LoginListener;

/**
 * Created by Liz on 2/7/2016.
 */
public class BoredatApplication extends Application {
    private AppComponent appComponent;
    private SessionComponent sessionComponent;
    private UnauthorizedComponent unauthorizedComponent;

    public static BoredatApplication get(Context context){
        return (BoredatApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();

    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public SessionComponent createSessionComponent(Account account, AccessToken accessToken) {
        sessionComponent = appComponent.plus(new SessionModule(account, accessToken));
        return sessionComponent;
    }

    public UnauthorizedComponent createUnauthorizedComponent(LoginListener loginListener) {
        unauthorizedComponent = appComponent.plus(new UnauthorizedModule(loginListener));
        return unauthorizedComponent;
    }

    public void releaseSessionComponent() {
        sessionComponent = null;
    }

    public void releaseUnauthorizedComponent() {
        unauthorizedComponent = null;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public SessionComponent getSessionComponent() {
        return sessionComponent;
    }

    public UnauthorizedComponent getUnauthorizedComponent() {
        return unauthorizedComponent;
    }
}
