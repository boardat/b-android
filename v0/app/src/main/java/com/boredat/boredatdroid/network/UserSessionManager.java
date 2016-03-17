package com.boredat.boredatdroid.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.boredat.boredatdroid.Login.LoginActivity;
import com.boredat.boredatdroid.util.Constants;

/**
 * Created by Liz on 7/19/2015.
 */
public class UserSessionManager {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context mCtx;

    public UserSessionManager(Context context) {
        this.mCtx = context.getApplicationContext();
        prefs = mCtx.getSharedPreferences(Constants.PREFS_NAME,Constants.PRIVATE_MODE);
        editor = prefs.edit();
    }

    /* Create login session
    * */
    public void createUserLoginSession(String token, String secret) {
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, true);
        editor.putString(Constants.KEY_ACCESS_TOKEN, token);
        editor.putString(Constants.KEY_ACCESS_TOKEN_SECRET, secret);
        editor.commit();
    }

    /* Store session details
    * */
    public void setSessionDetails( String endpoint, String url,
                                       String network, String networkShort,
                                       String personalityId, String personalityName ) {
        editor.putString(Constants.KEY_LOCAL_ENDPOINT, endpoint);
        editor.putString(Constants.KEY_LOCAL_URL, url);
        editor.putString(Constants.KEY_NETWORK_LONG, network);
        editor.putString(Constants.KEY_NETWORK_SHORT, networkShort);
        editor.putString(Constants.KEY_PERSONALITY_ID, personalityId);
        editor.putString(Constants.KEY_PERSONALITY_NAME, personalityName);
        editor.putString(Constants.KEY_LOCAL_BOARD_TITLE, createLocalBoardTitle(networkShort));
        editor.commit();
    }

    private static String createLocalBoardTitle(String networkShort) {
        return "Bored at " + networkShort.substring(0, 1).toUpperCase() + networkShort.substring(1);
    }

    public void setCurrentPage(int pageNum) {
        editor.putInt(Constants.KEY_CURRENT_PAGE_LOCAL, pageNum);
        editor.commit();
    }

    public int getCurrentPage() {
        return prefs.getInt(Constants.KEY_CURRENT_PAGE_LOCAL,1);
    }
    
    public String getLocalBoardTitle() {
        return prefs.getString(Constants.KEY_LOCAL_BOARD_TITLE, "Bored at Home");
    }

    /* Store user image url
     */
    public void setUserImageUrl(String url) {
        editor.putString(Constants.KEY_USER_IMAGE_URL, url);
        editor.commit();
    }


    /* Check user login status
    * If false redirect user to login page
    * */
    public boolean checkLogin() {
        if (!this.isUserLoggedIn()) {
            // Redirect to LoginActivity
            Intent i = new Intent(mCtx, LoginActivity.class);

            // Close all Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Starting LoginActivity
            mCtx.startActivity(i);

            return true;
        }
        return false;
    }

    /* Get stored session data
    * */
    public String[] getAccess() {
        String token = prefs.getString(Constants.KEY_ACCESS_TOKEN, Constants.PREFS_DEFAULT_STR_VALUE);
        String secret = prefs.getString(Constants.KEY_ACCESS_TOKEN_SECRET, Constants.PREFS_DEFAULT_STR_VALUE);

        String[] access = new String[2];
        access[0] = token;
        access[1] = secret;
        return access;
    }

    public String getLocalEndpoint() {
        return prefs.getString(Constants.KEY_LOCAL_ENDPOINT, Constants.PREFS_DEFAULT_STR_VALUE);
    }

    public String getLocalUrl() {
        return prefs.getString(Constants.KEY_LOCAL_URL, Constants.PREFS_DEFAULT_STR_VALUE);
    }

    public String getUserImageUrl() {
        return prefs.getString(Constants.KEY_USER_IMAGE_URL, Constants.PREFS_DEFAULT_STR_VALUE);
    }

    public String getNetworkShortname() {
        return prefs.getString(Constants.KEY_NETWORK_SHORT, Constants.PREFS_DEFAULT_STR_VALUE);
    }

    /**
    * Clear session details
    * */
    public void logoutUser() {
        // Clear user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to LoginActivity
        Intent i = new Intent(mCtx, LoginActivity.class);

        // Close all activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting LoginActivity
        mCtx.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn() {
        return prefs.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
    }
}
