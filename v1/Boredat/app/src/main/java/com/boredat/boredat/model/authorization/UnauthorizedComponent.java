package com.boredat.boredat.model.authorization;

import com.boredat.boredat.activities.SilentLoginActivity;
import com.boredat.boredat.fragments.LoginFragment;

import dagger.Subcomponent;

/**
 * Created by Liz on 2/17/2016.
 */
@UnauthorizedScope
@Subcomponent(
        modules = {
                UnauthorizedModule.class
        }
)
public interface UnauthorizedComponent {
    LoginFragment inject(LoginFragment loginFragment);
    SilentLoginActivity inject(SilentLoginActivity silentLoginActivity);
}
