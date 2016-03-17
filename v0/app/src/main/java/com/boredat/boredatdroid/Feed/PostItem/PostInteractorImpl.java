package com.boredat.boredatdroid.Feed.PostItem;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boredat.boredatdroid.models.ServerMessage;
import com.boredat.boredatdroid.network.BoredatPostRequest;
import com.boredat.boredatdroid.network.RQSingleton;
import com.boredat.boredatdroid.network.UserSessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liz on 10/18/2015.
 */
public class PostInteractorImpl implements PostInteractor {
    @Override
    public void agree(String postId, Context mCtx, final OnVotedListener listener, UserSessionManager session) {
        Log.d("", "Interactor => agree( " + postId + " )");

        String[] mAccess = session.getAccess();
        String url = session.getLocalEndpoint() + "/post/agree";

        Map<String,String> mParams = new HashMap<String,String>();
        mParams.put("id", String.valueOf(postId));

        Request agreeRequest = new BoredatPostRequest<>(Request.Method.POST, url, mParams, ServerMessage.class, mAccess, null, new Response.Listener<ServerMessage>() {
            @Override
            public void onResponse(ServerMessage response) {
                if (!response.isError()) {
                    listener.onAgreeSuccess(response);
                } else {
                    listener.onVoteError(response.getError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });
        RQSingleton.getInstance(mCtx).getRequestQueue().add(agreeRequest);
    }

    @Override
    public void disagree(String postId, Context mCtx, final OnVotedListener listener, UserSessionManager session) {
        Log.d("", "Interactor => disagree( " + postId + " )");

        String[] mAccess = session.getAccess();
        String url = session.getLocalEndpoint() + "/post/disagree";
        Log.d("", "==> URL: " + url);
        Map<String,String> mParams = new HashMap<String,String>();
        mParams.put("id", String.valueOf(postId));

        Request disagreeRequest = new BoredatPostRequest<>(Request.Method.POST, url, mParams, ServerMessage.class, mAccess, null, new Response.Listener<ServerMessage>() {
            @Override
            public void onResponse(ServerMessage response) {
                if (!response.isError()) {
                    listener.onDisagreeSuccess(response);
                } else {
                    Log.d("", "Interactor => Error =====>" + response.getError().getMessage());

                    listener.onVoteError(response.getError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });
        RQSingleton.getInstance(mCtx).getRequestQueue().add(disagreeRequest);
    }

    @Override
    public void newsworthy(String postId, Context mCtx, final OnVotedListener listener, UserSessionManager session) {
        Log.d("", "Interactor => newsworthy( " + postId + " )");

        String[] mAccess = session.getAccess();
        String url = session.getLocalEndpoint() + "/post/newsworthy";

        Map<String, String> mParams = new HashMap<String, String>();
        mParams.put("id", String.valueOf(postId));

        Request newsworthyRequest = new BoredatPostRequest<>(Request.Method.POST, url, mParams, ServerMessage.class, mAccess, null, new Response.Listener<ServerMessage>() {
            @Override
            public void onResponse(ServerMessage response) {
                if (!response.isError()) {
                    listener.onNewsworthySuccess(response);
                } else {
                    listener.onVoteError(response.getError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });
        RQSingleton.getInstance(mCtx).getRequestQueue().add(newsworthyRequest);
    }
}
