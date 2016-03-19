package com.boredat.boredat;

import android.content.SharedPreferences;

import com.boredat.boredat.model.authorization.AccessToken;
import com.boredat.boredat.model.authorization.Account;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Liz on 3/19/2016.
 */
public class SessionManager {
    // Constants
    private static final String KEY_ACCOUNT = "sessionAccount";
    private static final String KEY_ACCESS_TOKEN = "sessionAccessToken";
    private static final String KEY_IS_USER_LOGGED_IN = "sessionIsUserLoggedIn";


    // Member variables
    private SharedPreferences prefs;
    private Gson gson;

    public SessionManager(SharedPreferences prefs, Gson gson) {
        this.prefs = prefs;
        this.gson = gson;
    }

    public void setSessionLogin(Account account, AccessToken accessToken) {
        saveSessionAccount(account);
        saveAccessToken(accessToken);
        setUserLoggedIn(true);
    }

    public void logout() {
        setUserLoggedIn(false);
    }

    public Account getSessionAccount() {
        String json = getSavedString(KEY_ACCOUNT);
        if (json != null) {
            Type type = new TypeToken<Account>(){}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    public AccessToken getSessionAccessToken() {
        String json = getSavedString(KEY_ACCESS_TOKEN);
        if (json != null) {
            Type type = new TypeToken<AccessToken>(){}.getType();
            return gson.fromJson(json, type);
        }
        return null;
    }

    public boolean isUserLoggedIn() {
        return getSavedBoolean(KEY_IS_USER_LOGGED_IN, false);
    }

    private void saveSessionAccount(Account account) {
        saveString(KEY_ACCOUNT, gson.toJson(account));

    }

    private void saveAccessToken(AccessToken accessToken) {
        saveString(KEY_ACCESS_TOKEN, gson.toJson(accessToken));
    }

    private void setUserLoggedIn(boolean isUserLoggedIn) {
        saveBoolean(KEY_IS_USER_LOGGED_IN, isUserLoggedIn);
    }

    private void saveString(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getSavedString(String key) {
        return prefs.getString(key, null);
    }

    private void saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private boolean getSavedBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }


}
