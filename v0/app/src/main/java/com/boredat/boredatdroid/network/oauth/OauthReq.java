package com.boredat.boredatdroid.network.oauth;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.boredat.boredatdroid.Login.OnLoginListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class OauthReq extends Request<OauthResponse> {

    private Gson gson;
    private Class<OauthResponse> clazz;
    private String mUrl;
    private OnLoginListener mOnLoginListener;

    public OauthReq(int method, String url, Response.ErrorListener errorListener, OnLoginListener listener) {
        super(method, url, errorListener);
        mUrl=url;
        mOnLoginListener = listener;
        gson=new GsonBuilder().create();
        clazz = OauthResponse.class;
    }


    @Override
    protected Response<OauthResponse> parseNetworkResponse(NetworkResponse response) {
        String json = null;
        try {
            json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
        if (json.length() > 0) {
            Log.d("", "Response => " + json.toString());
            return Response.success(gson.fromJson(json, clazz), HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return Response.success(new OauthResponse(), HttpHeaderParser.parseCacheHeaders(response));
        }
        }catch (UnsupportedEncodingException e) {
           return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(OauthResponse response) {
        if (response!=null && mOnLoginListener!=null) {
            Log.d("", "deliverResponse Response => " + response.toString());
            mOnLoginListener.onTokenReceived(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return BuildOauthAuthorize.build(mUrl, null, null);
    }
}
