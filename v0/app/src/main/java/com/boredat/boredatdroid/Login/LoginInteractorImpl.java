package com.boredat.boredatdroid.Login;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boredat.boredatdroid.models.Personality;
import com.boredat.boredatdroid.models.User;
import com.boredat.boredatdroid.network.BoredatRequest;
import com.boredat.boredatdroid.network.RQSingleton;
import com.boredat.boredatdroid.network.UserSessionManager;
import com.boredat.boredatdroid.network.oauth.OauthLoginAccess;
import com.boredat.boredatdroid.network.oauth.OauthLoginReq;
import com.boredat.boredatdroid.network.oauth.OauthLoginResponse;
import com.boredat.boredatdroid.network.oauth.OauthReq;
import com.boredat.boredatdroid.network.oauth.OauthResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liz on 7/12/2015.
 */
public class LoginInteractorImpl implements LoginInteractor {
    private String mOauthRequestUrl = "https://boredat.com/global/api/v1/oauth/request_token";
    private String mOauthLoginUrl = "https://boredat.com/global/api/v1/oauth/login";
    private String mOauthLoginAccess = "https://boredat.com/global/api/v1/oauth/access_token";
    private String mGetPersonalUrl = "https://boredat.com/global/api/v1/user";

    @Override
    public void requestToken(Context ctx, OnLoginListener listener) {
        Log.d("", "Interactor => requestToken");
        OauthReq jsonOauthRequest = new OauthReq(Request.Method.POST, mOauthRequestUrl, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("", "Error => " + error.toString());
                }
            }, listener);
            Log.d("", jsonOauthRequest.toString() + " ");

        RQSingleton.getInstance(ctx).addToRequestQueue(jsonOauthRequest);
       // RQSingleton.getInstance(ctx).getRequestQueue().add(jsonOauthRequest);
    }

    @Override
    public void login(String username, String password, Context ctx, OauthResponse response, OnLoginListener listener) {
        Log.d("", "Interactor => login");
        String url=mOauthLoginUrl+"?login="+username+"&pass="+password+"&oauth_token="+response.oauth_token;
        OauthLoginReq loginReq = new OauthLoginReq(Request.Method.GET, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        }, listener);
        RQSingleton.getInstance(ctx).getRequestQueue().add(loginReq);
    }

    @Override
    public void requestAccess(Context mCtx, OauthLoginResponse response, String mTokenSecret, OnLoginListener listener) {
        Log.d("", "Interactor => requestAccess");
        OauthLoginAccess loginAccess = new OauthLoginAccess(Request.Method.POST, mOauthLoginAccess, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());

            }
        }, listener, response.oauth_token, mTokenSecret);
        RQSingleton.getInstance(mCtx).getRequestQueue().add(loginAccess);
    }


    @Override
    public void getUserInfo(Context mCtx, final OnLoginListener loginListener, UserSessionManager sessionManager) {
        Log.d("", "Interactor => getUserInfo");
        String[] mAccess = sessionManager.getAccess();
        Log.d("", "access token : " + mAccess[0] + "\naccess secret : " + mAccess[1]);

        Map<String,String> mParams = new HashMap<String,String>();
        mParams.put("fields", "networkShortname,networkName,personalityId,personalityName,userTotalNotifications");

        Request getUserRequest = new BoredatRequest<>(Request.Method.GET, mGetPersonalUrl, mParams, User.class, mAccess, null, new Response.Listener<User>() {

            @Override
            public void onResponse(User user) {
                if (user.isError()) {
                    loginListener.onGetUserError(user);
                } else {
                    loginListener.onGetUserSuccess(user);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });

        RQSingleton.getInstance(mCtx).getRequestQueue().add(getUserRequest);    }

    @Override
    public void getPersonalityImage(String personalityID, Context mCtx, final OnLoginListener loginListener, UserSessionManager sessionManager) {
        Log.d("", "Interactor => getPersonalityImage");

        String[] mAccess = sessionManager.getAccess();
        String url = sessionManager.getLocalEndpoint() + "/personality";

        Map<String,String> mParams = new HashMap<String,String>();
        mParams.put("id", personalityID);
        mParams.put("fields", "image");

        Request getPersonalityImageRequest = new BoredatRequest<>(Request.Method.GET, url, mParams, Personality.class, mAccess, null, new Response.Listener<Personality>() {

            @Override
            public void onResponse(Personality personality) {
                if (personality.isError()) {
                    loginListener.onGetPersonalityError(personality);
                } else {
                    loginListener.onGetPersonalitySuccess(personality);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });

        RQSingleton.getInstance(mCtx).getRequestQueue().add(getPersonalityImageRequest);
    }
}
