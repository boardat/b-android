package com.boredat.boredat.util;

/**
 * Created by Liz on 11/30/2015.
 */
public class Constants {
    final static public String TAG = "BoredatForAndroid"; // log or request TAG
    final static public String PREFS_NAME = "BoredatPrefs";
    final static public int PRIVATE_MODE = 0;

    final static public String API_ENDPOINT = "https://boredat.com/api/v1/";
    final static public String API_GLOBAL_ENDPOINT = "https://boredat.com/global/api/v1/";

    public static final String CONSUMER_KEY="d81a0dc62eaa717532d134db065270cb04fab84c1";
    public static final String CONSUMER_SECRET="f7a543570d6a6efdd38d7a9e3a51a2bd";
    public static final String REQUEST_TOKEN_URL="https://boredat.com/global/api/v1/oauth/request_token";
    public static final String LOGIN_URL="https://boredat.com/global/api/v1/oauth/login";
    public static final String ACCESS_TOKEN_URL="https://boredat.com/global/api/v1/oauth/access_token";


    final static public int FEED_ID_LOCAL = 0;
    final static public int FEED_ID_GLOBAL = 1;
    final static public int FEED_ID_WEEKS_BEST = 2;
    final static public int FEED_ID_WEEKS_WORST = 3;
    final static public int FEED_ID_WEEKS_CONTROVERSIAL = 4;
}
