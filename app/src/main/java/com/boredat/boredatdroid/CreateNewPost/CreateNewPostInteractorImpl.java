package com.boredat.boredatdroid.CreateNewPost;

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
 * Created by Liz on 10/22/2015.
 */
public class CreateNewPostInteractorImpl implements CreateNewPostInteractor {
    @Override
    public void post(String text, boolean anonymously, int locationId, String id, Context context, final OnFinishedPostListener listener, UserSessionManager session) {
        Log.d("", "Interactor => post( " + text + "," + anonymously + " )");

        String[] mAccess = session.getAccess();
        String url = session.getLocalEndpoint() + "/post";

        /**
         * Required params
         **/

        // text - the text of the post
        Map<String,String> mParams = new HashMap<String,String>();
//        String oauthEncodeText = OauthHeader.encode(text);
//        String utf8EncodeText = "";
//        try {
//            utf8EncodeText = URLEncoder.encode(text,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        mParams.put("text", text);

        /**
        * Optional params
        **/

        // anonymously - default is 1
        if (!anonymously) {
            mParams.put("anonymously","0");
        }

        // locationId - default is 0
        if (locationId != 0) {
            mParams.put("locationId",String.valueOf(locationId));
        }

        // id - default is null
//        if (id != null) {
//            mParams.put("id",id);
//        }

        // reply to post for debugging purposes
        mParams.put("id","4995638");

        Request newPostRequest = new BoredatPostRequest<>(Request.Method.POST, url, mParams, ServerMessage.class, mAccess, null, new Response.Listener<ServerMessage>() {
            @Override
            public void onResponse(ServerMessage response) {
                if (!response.isError()) {
                    listener.onPostSuccessListener(response);
                } else {
                    listener.onPostErrorListener(response.getError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });
        RQSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(newPostRequest);
    }
}
