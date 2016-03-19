package com.boredat.boredat.model.authorization;

import com.boredat.boredat.util.Constants;

/**
 * Created by Liz on 2/6/2016.
 */
public class AccessToken {
    private String mConsumerKey = Constants.CONSUMER_KEY;
    private String mConsumerSecret = Constants.CONSUMER_SECRET;
    private String mBaseUrl = Constants.API_ENDPOINT;
    private String mGlobalUrl = Constants.API_GLOBAL_ENDPOINT;
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

    public String getGlobalUrl() {return mGlobalUrl;}

    public String getToken() {
        return mToken;
    }

    public String getTokenSecret() {
        return mTokenSecret;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ((o== null) || (o.getClass() != this.getClass())) return false;

        // object is an AccessToken
        AccessToken a = (AccessToken) o;
        return mToken.equals(a.getToken());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (null == mToken ? 0 : mToken.hashCode());
        return hash;
    }
}
