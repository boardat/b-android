package com.boredat.boredat;

import com.boredat.boredat.activities.SplashActivity;
import com.boredat.boredat.fragments.UserFragment;
import com.boredat.boredat.model.api.SessionComponent;
import com.boredat.boredat.model.api.SessionModule;
import com.boredat.boredat.model.authorization.UnauthorizedComponent;
import com.boredat.boredat.model.authorization.UnauthorizedModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Liz on 2/7/2016.
 */
@Singleton
@Component(
        modules = {
                AppModule.class
        }
)
public interface AppComponent {
    SessionComponent plus(SessionModule module);
    UnauthorizedComponent plus(UnauthorizedModule module);

    SplashActivity inject(SplashActivity splashActivity);
    UserFragment inject(UserFragment userFragment);
}
