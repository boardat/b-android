package com.boredat.boredatdroid.network.oauth;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.boredat.boredatdroid.Login.OnLoginListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;


public class OauthLoginReq extends Request<OauthLoginResponse> {

    private Gson gson;
    private Class<OauthLoginResponse> clazz;
    private OnLoginListener mOnLoginListener;

    public OauthLoginReq(int method, String url, Response.ErrorListener errorListener, OnLoginListener listener) {
        super(method, url, errorListener);
        gson=new GsonBuilder().create();
        clazz = OauthLoginResponse.class;
        mOnLoginListener = listener;
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
        mOnLoginListener.onVerifierReceived(response);
    }
}
