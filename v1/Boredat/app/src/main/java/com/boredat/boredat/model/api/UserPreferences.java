package com.boredat.boredat.model.api;

import android.content.SharedPreferences;

import com.boredat.boredat.model.api.responses.UserResponse;
import com.boredat.boredat.model.authorization.Account;

/**
 * Created by Liz on 2/7/2016.
 */
public class UserPreferences {
    // Constants
    public static final String KEY_NETWORK_NAME = "userNetworkName";
    public static final String KEY_NETWORK_SHORT = "userNetworkShortname";
    public static final String KEY_PERSONALITY_NAME = "userPersonalityName";
    public static final String KEY_PERSONALITY_ID = "userPersonalityId";
    public static final String KEY_HAS_USER_DETAILS = "hasUserDetails";

    public static final String PREF_USE_LOCATION = "prefShouldUseLocationDefault";
    public static final String PREF_USE_PERSONALITY = "prefShouldUsePersonalityDefault";

    // Member variables
    private SharedPreferences prefs;
    private String userKeyPrefix;

    // Constructor
    public UserPreferences(SharedPreferences prefs, Account userAccount) {
        this.prefs = prefs;
        String userId = userAccount.getUserId();
        userKeyPrefix = String.format("user:%s/", userId);
    }

    // Methods
    public void saveUserDetails(UserResponse user) {
        saveNetworkName(user.getNetworkName());
        saveNetworkShortname(user.getNetworkShortname());
        savePersonalityName(user.getPersonalityName());
        savePersonalityId(user.getPersonalityId());

        saveBoolean(KEY_HAS_USER_DETAILS, true);
    }

    public void saveUseLocationPref(boolean value) {
        saveBoolean(PREF_USE_LOCATION, value);
    }

    public void saveUsePersonalityPref(boolean value) {
        saveBoolean(PREF_USE_PERSONALITY, value);
    }

    public String getNetworkName() {
        return getSavedString(getUserKey(KEY_NETWORK_NAME));
    }

    public String getNetworkShortname() {
        return getSavedString(getUserKey(KEY_NETWORK_SHORT));
    }

    public String getPersonalityName() {
        return getSavedString(getUserKey(KEY_PERSONALITY_NAME));
    }

    public String getPersonalityId() {
        return getSavedString(getUserKey(KEY_PERSONALITY_ID));
    }

    // Helper methods
    private void saveNetworkName(String network) {
        saveString(getUserKey(KEY_NETWORK_NAME), network);
    }

    private void saveNetworkShortname(String shortname) {
        saveString(getUserKey(KEY_NETWORK_SHORT), shortname);
    }

    private void savePersonalityName(String name) {
        saveString(getUserKey(KEY_PERSONALITY_NAME), name);
    }

    private void savePersonalityId(String id) {
        saveString(getUserKey(KEY_PERSONALITY_ID), id);
    }

    private String getUserKey(String key) {
        return userKeyPrefix + key;
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
