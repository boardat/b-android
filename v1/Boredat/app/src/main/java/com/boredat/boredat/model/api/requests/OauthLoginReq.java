package com.boredat.boredat.model.api.requests;

/**
 * Created by Liz on 11/30/2015.
 */
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.boredat.boredat.presentation.Login.OauthListener;
import com.boredat.boredat.model.api.responses.OauthLoginResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;


public class OauthLoginReq extends Request<OauthLoginResponse> {

    private Gson gson;
    private Class<OauthLoginResponse> clazz;
    private OauthListener mTokenReceived;

    public OauthLoginReq(int method, String url, Response.ErrorListener listener, OauthListener tokenReceived) {
        super(method, url, listener);
        gson=new GsonBuilder().create();
        clazz = OauthLoginResponse.class;
        mTokenReceived = tokenReceived;
    }

    @Override
    protected Response<OauthLoginResponse> parseNetworkResponse(NetworkResponse response) {
        String json = null;
        try {
            json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            if (json.length() > 0) {
                Log.d("", "Response => " + json.toString());
                return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
            } else {
                return Response.success(new OauthLoginResponse(), HttpHeaderParser.parseCacheHeaders(response));
            }
        }catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(OauthLoginResponse response) {
        mTokenReceived.onVerifierReceived(response);
    }
}