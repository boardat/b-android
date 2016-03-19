package com.boredat.boredat.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.SessionManager;
import com.boredat.boredat.activities.SessionMainActivity;
import com.boredat.boredat.model.authorization.AccessToken;
import com.boredat.boredat.model.authorization.Account;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.authorization.Authenticator;
import com.boredat.boredat.model.authorization.SavedAccountsManager;
import com.boredat.boredat.presentation.Login.ILoginPresenter;
import com.boredat.boredat.presentation.Login.LoginListener;
import com.boredat.boredat.presentation.Login.LoginView;
import com.devspark.robototextview.widget.RobotoAutoCompleteTextView;
import com.devspark.robototextview.widget.RobotoCheckBox;
import com.devspark.robototextview.widget.RobotoEditText;
import com.devspark.robototextview.widget.RobotoExtractEditText;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginView, LoginListener, AdapterView.OnItemSelectedListener {

    // Member Variables
    private ProgressDialog mDialog;

    @Inject
    Authenticator mAuthenticator;

    @Inject
    SavedAccountsManager mSavedAccountsManager;

    @Inject
    SessionManager sessionManager;

    @Bind(R.id.login_edit_user_id)
    RobotoAutoCompleteTextView mUserIdEditText;

    @Bind(R.id.login_edit_password)
    RobotoEditText mPasswordEditText;

    @Bind(R.id.login_remember_me)
    RobotoCheckBox mRememberMeCheckbox;

    @Bind(R.id.login_button)
    Button mLoginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, rootView);

        BoredatApplication.get(getActivity()).releaseSessionComponent();

        if (BoredatApplication.get(getActivity()).getUnauthorizedComponent() == null) {
            BoredatApplication.get(getActivity()).createUnauthorizedComponent(this);
            BoredatApplication.get(getActivity()).getUnauthorizedComponent().inject(this);
        }

        String[] ids = mSavedAccountsManager.getSavedUserIds();
        if (ids != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, mSavedAccountsManager.getSavedUserIds());
            mUserIdEditText.setAdapter(adapter);
            mUserIdEditText.setOnItemSelectedListener(this);
        }

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuthenticator != null) {
                    mAuthenticator.login(getUserId(), getPassword());
                }
            }
        });

        return rootView;
    }

    // LoginView
    @Override
    public String getUserId() {
        return mUserIdEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        if (mDialog == null) {
            // Set up ProgressDialog
            mDialog = new ProgressDialog(this.getActivity());
            mDialog.setTitle(getString(R.string.app_name));
            mDialog.setMessage(getString(R.string.dialog_logging_in));
            mDialog.setIndeterminate(true);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }

        if (mDialog != null) {
            mDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (mDialog != null) {
            mDialog.hide();
        }
    }

    // LoginListener
    @Override
    public void onVolleyError(VolleyError error) {
        showMessage(error.getMessage());
    }

    @Override
    public void onServerError(BoredatResponse.Error error) {
        showMessage(error.getMessage());
    }

    @Override
    public void onSuccess(Account account, AccessToken accessToken) {
        sessionManager.setSessionLogin(account, accessToken);
        if (mRememberMeCheckbox.isChecked()) {
            mSavedAccountsManager.saveAccount(account);
        }
        BoredatApplication.get(getActivity()).createSessionComponent(account, accessToken);
        BoredatApplication.get(getActivity()).releaseUnauthorizedComponent();
        navigateToSessionMainActivity();
    }

    private void navigateToSessionMainActivity() {
        startActivity(new Intent(getActivity(), SessionMainActivity.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String userId = (String) parent.getItemAtPosition(position);
        if (userId != null) {
            String savedPass = mSavedAccountsManager.getPasswordForUserId(userId);
            if (savedPass != null) {
                mPasswordEditText.setText(savedPass);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
