package com.boredat.boredat.model.api.responses;

/**
 * Created by Liz on 11/30/2015.
 */
public class OauthResponse extends BoredatResponse {
    public int oauth_callback_confirmed;
    public String oauth_token;
    public String oauth_token_secret;
    public int xoauth_token_ttl;
}
