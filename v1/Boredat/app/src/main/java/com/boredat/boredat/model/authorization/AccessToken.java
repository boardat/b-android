package com.boredat.boredat.model.authorization;

import com.boredat.boredat.util.Constants;

/**
 * Created by Liz on 2/6/2016.
 */
public class AccessToken {
    private String mConsumerKey = Constants.CONSUMER_KEY;
    private String mConsumerSecret = Constants.CONSUMER_SECRET;
    private String mBaseUrl = Constants.API_ENDPOINT;
    private String mToken;
    private String mTokenSecret;

    public AccessToken(String token, String secret) {
        this.mToken = token;
        this.mTokenSecret = secret;
    }

    public String getConsumerSecret() {
        return mConsumerSecret;
    }

    public String getConsumerKey() {
        return mConsumerKey;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public String getToken() {
        return mToken;
    }

    public String getTokenSecret() {
        return mTokenSecret;
    }
}
