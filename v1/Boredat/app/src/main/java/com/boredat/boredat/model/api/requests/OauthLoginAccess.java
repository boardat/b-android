package com.boredat.boredat.model.api.requests;

/**
 * Created by Liz on 11/30/2015.
 */
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.boredat.boredat.presentation.Login.OauthListener;
import com.boredat.boredat.util.oauth.BuildOauthAuthorize;
import com.boredat.boredat.model.api.responses.OauthResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class OauthLoginAccess  extends Request<OauthResponse> {

    private Gson gson;
    private Class<OauthResponse> clazz;
    private String mUrl;
    private OauthListener mOauthListener;
    private String mOauthToken;
    private String mOauthSecret;

    public OauthLoginAccess(int method, String url, Response.ErrorListener listener, OauthListener tokenReceived, String oauthToken, String oauthSecret) {
        super(method, url, listener);
        mUrl=url;
        mOauthListener =tokenReceived;
        gson=new GsonBuilder().create();
        clazz = OauthResponse.class;
        mOauthSecret = oauthSecret;
        mOauthToken = oauthToken;
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
        if (response!=null && mOauthListener !=null) {
            Log.d("", "deliverResponse Response => " + response.toString());
            mOauthListener.onAccessTokenReceived(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return BuildOauthAuthorize.build(mUrl, mOauthToken, mOauthSecret);
    }
}