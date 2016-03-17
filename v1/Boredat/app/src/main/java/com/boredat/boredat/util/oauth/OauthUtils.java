package com.boredat.boredat.util.oauth;

import com.boredat.boredat.util.Constants;

/**
 * Created by Liz on 2/6/2016.
 */
public class OauthUtils {
    public static String getLoginRequestURL(String userId, String password, String token) {
        String url = Constants.LOGIN_URL
                + "?login=" + userId
                + "&pass=" + password
                + "&oauth_token=" + token;

        return url;
    }
}
