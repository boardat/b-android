package com.boredat.boredatdroid.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.boredat.boredatdroid.models.PostFeed;
import com.boredat.boredatdroid.network.oauth.OauthHeader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Liz on 7/2/2015.
 */
/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson
 */
public class BoredatRequest<T extends BoredatResponse> extends Request<T> {
    private Gson gson;
    private Class<T> clazz;
    private Listener<T> mListener;
    private String mUrl;
    private Map<String, String> mParams;
    private String mRequestMethod;
    private String mOauthToken;
    private String mOauthSecret;
    private String mRequestBody;

    /**
     * Make a specified Http Request and return a parsed object from JSON.
     *
     * @param method        The Http Method type as specified by Request.Method
     * @param url           URL of the request to make
     * @param clazz         Relevant class object, for Gson's reflection
     * @param errorListener Callback interface for delivering error responses
     */
    public BoredatRequest(int method,
                          String url,
                          Map<String, String> params,
                          Class<T> clazz,
                          String[] accessTokens,
                          String requestBody,
                          Listener<T> listener,
                          Response.ErrorListener errorListener) {

        super(method, buildUrl(url,params), errorListener);

        // gson adapters
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(PostFeed.class, new PostFeedDeserializer())
                .registerTypeAdapter(String.class, new EscapeStringDeserializer())
                .setDateFormat("yyyy-MM-dd H:m:s")
                .disableHtmlEscaping();
        this.gson = builder.create();


        this.clazz = clazz;
        this.mUrl = url;
        this.mParams = params;
        this.mOauthToken = accessTokens[0];
        this.mOauthSecret = accessTokens[1];
        this.mListener = listener;

        Log.d("", "Request URL => " + mUrl);

        // used to generate oauth header
        if (method == Request.Method.POST) {
            mRequestMethod = "POST";
        } else {
            mRequestMethod = "GET";
        }
    }

    private static String buildUrl(String base, Map<String,String> params) {

        String temp = base;
        if (params != null) {
            temp += "?";
            for (String paramName : params.keySet()) {
                temp += "&" + paramName + "=" + params.get(paramName);
            }
        }
        Log.d("","BUILD URL =====> "+ temp);
        return temp;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String json = null;
        try {
            json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
                Log.d("", "Response => " + json.toString());

                return Response.success( gson.fromJson(json,clazz), HttpHeaderParser.parseCacheHeaders(response));

        }catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }


    @Override
    protected void deliverResponse(T response) {
        if (response != null) {
            Log.d("", "deliverResponse Response => " + response.toString());

            mListener.onResponse(response);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return OauthHeader.getOauthHeader(mRequestMethod,mUrl,mParams,mOauthToken,mOauthSecret);
    }
}
