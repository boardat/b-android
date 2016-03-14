package com.boredat.boredatdroid.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.boredat.boredatdroid.R;
import com.boredat.boredatdroid.activities.MainActivity;
import com.boredat.boredatdroid.network.RQSingleton;
import com.boredat.boredatdroid.network.UserSessionManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class LoginActivity extends Activity implements LoginView {
    private ProgressBar mProgressBar;
    private EditText mUserId;
    private EditText mPassword;
    private Button mLoginButton;
    private Button mRegisterButton;

    private TextView mResultText;
    private RequestQueue mReqQueue;
    private LoginPresenter mPresenter;

    private WebView mWebView;
    private WebSettings mWebSettings;


    // User Session Manager Class
    UserSessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mUserId = (EditText) findViewById(R.id.login_edit_user_id);
        mPassword = (EditText) findViewById(R.id.login_edit_pswd);
        mLoginButton = (Button)findViewById(R.id.login_button);

        // buttons
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mLoginButton.setOnClickListener(loginOnClickListener);
        mRegisterButton.setOnClickListener(registerOnClickListener);

        mResultText = (TextView)findViewById(R.id.result_text);
        mReqQueue= RQSingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        mPresenter = new LoginPresenterImpl(this, this.getApplicationContext());
    }

    /* NEW */

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public String getUsername() {
        return String.valueOf(mUserId.getText());
    }

    @Override
    public String getPassword() {
        return String.valueOf(mPassword.getText());
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        Log.d("", "Enter username");
    }

    @Override
    public void setPasswordError() {
        Log.d("", "Enter password");
    }

    @Override
    public void setNetworkError(int errorNo, String errorMessage) {

        Log.d("", "Set network error => errorNo : " + errorNo
        + "\nerrorMessage : " + errorMessage);

        mResultText.setText("ERROR : \n" +  "errorNo : " + errorNo
                + "\nerrorMessage : " + errorMessage);
    }

    @Override
    public void navigateToHome() {
//        startActivity(new Intent(this.getApplicationContext(), MainActivity.class));
//        finish();

        // Starting MainActivity
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        finish();
    }

    // - Listener to handle login clicks
    View.OnClickListener loginOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("", "Clicked login");
            mPresenter.validateCredentials(session);
        }
    };

    /* REGISTER */

    // - Onclick listener to handle register button.
    // -    Opens a webview which sort of shows the browser version of b@.
    View.OnClickListener registerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setContentView(R.layout.register_webview);
            mWebView = (WebView) findViewById(R.id.register_webview_screen);
            mWebView.setWebViewClient(new WebViewClient());
            mWebSettings = mWebView.getSettings();
            mWebSettings.setJavaScriptEnabled(true);
            mWebSettings.setDomStorageEnabled(true);

            // Spawn a worker thread to do some network stuff. See loadRegisterWebView.class below
            new loadRegisterWebview().execute();
        }
    };

    // - This class strips unnecessary HTML from the web version of b@ and lets us display only relevant fields
    private class loadRegisterWebview extends AsyncTask<Void, Void, String>{

        //Hardcoded background just to make it a little less ugly.
        String style = "<style> \r\n body {background-image: url(\"https://boredat.com/global/images/756_personalities_transparent.png\");background-repeat: repeat;} \r\n </style>";

        //After stripping unwanted HTML, run in our webview the isolated content
        @Override
        protected void onPostExecute(String result) {
            String mime = "text/html";
            String encoding = "utf-8";
            String baseURL = "https://www.boredat.com";
            mWebView.loadDataWithBaseURL(baseURL, style + result, mime, encoding, baseURL);
        }

        //Have to execute web requests in worker thread, which is why this whole thing exists.
        //Note Jsoup dependency.
        @Override
        protected String doInBackground(Void... arg0) {
            try {
                Document strip = Jsoup.connect("https://www.boredat.com").timeout(10000).get();
                Elements element = strip.select("div#signup");
                String html = element.toString();
                return html;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}