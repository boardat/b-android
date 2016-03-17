package com.boredat.boredat.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.boredat.boredat.R;
import com.boredat.boredat.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, LoginFragment.newInstance())
                .commit();
    }


    

    @Override
    public void onBackPressed() {
        // Disable going back to SessionScope activity
        moveTaskToBack(true);
    }
}
