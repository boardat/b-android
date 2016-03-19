package com.boredat.boredat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.SessionManager;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.authorization.AccessToken;
import com.boredat.boredat.model.authorization.Account;
import com.boredat.boredat.model.authorization.Authenticator;
import com.boredat.boredat.presentation.Login.LoginListener;

import javax.inject.Inject;

public class SilentLoginActivity extends AppCompatActivity implements LoginListener{
    // Constants
    public static final String KEY_USER_ID = "keyUserId";
    public static final String KEY_PASSWORD = "keyPassword";

    // Member variables
    @Inject
    Authenticator mAuthenticator;

    @Inject
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silent_login);

        Intent intent = getIntent();
        String userId = intent.getStringExtra(KEY_USER_ID);
        String password = intent.getStringExtra(KEY_PASSWORD);

        BoredatApplication.get(this).releaseSessionComponent();

        if (userId == null || password == null) {
            navigateToLoginActivity();
        } else {
            if (BoredatApplication.get(this).getUnauthorizedComponent() == null) {
                BoredatApplication.get(this).createUnauthorizedComponent(this);
                BoredatApplication.get(this).getUnauthorizedComponent().inject(this);
            }
            if (mAuthenticator != null) {
                mAuthenticator.login(userId, password);
            }
        }
    }

    @Override
    public void onVolleyError(VolleyError error) {
        showMessage(error.getMessage());
    }

    @Override
    public void onServerError(BoredatResponse.Error error) {
        showMessage(error.getMessage());
        navigateToLoginActivity();
    }

    @Override
    public void onSuccess(Account account, AccessToken accessToken) {
        sessionManager.setSessionLogin(account, accessToken);
        BoredatApplication.get(this).createSessionComponent(account, accessToken);
        BoredatApplication.get(this).releaseUnauthorizedComponent();
        navigateToSessionMainActivity();
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void navigateToSessionMainActivity() {
        startActivity(new Intent(this, SessionMainActivity.class));
    }

    private void showMessage(String text) {
        Toast.makeText(SilentLoginActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
