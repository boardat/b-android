package com.boredat.boredatdroid.Feed;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.boredat.boredatdroid.models.PostFeed;
import com.boredat.boredatdroid.network.BoredatRequest;
import com.boredat.boredatdroid.network.RQSingleton;
import com.boredat.boredatdroid.network.UserSessionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liz on 7/13/2015.
 */
public class FeedInteractorImpl implements FeedInteractor {
    @Override
    public void loadPage(int page, Context mCtx, final OnLoadedFeedListener listener, UserSessionManager sessionManager) {
        Log.d("", "Interactor => loadPage " + page);
        String[] mAccess = sessionManager.getAccess();
        String url = sessionManager.getLocalEndpoint() + "/posts";

        Map<String,String> mParams = new HashMap<String,String>();
        mParams.put("page", String.valueOf(page));
        mParams.put("fields", "postId,postCreated,postText,postTotalAgrees,postTotalDisagrees,postTotalNewsworthies,postTotalReplies,screennameId,screennameName,screennameImage,hasVotedAgree,hasVotedDisagree,hasVotedNewsworthy");

        Request pageRequest = new BoredatRequest<>(Request.Method.GET, url, mParams, PostFeed.class, mAccess, null, new Response.Listener<PostFeed>() {
            @Override
            public void onResponse(PostFeed response) {
                if (!response.isError()) {
                    listener.onLoadedFeedSuccess(response);
                } else {
                    listener.onLoadedFeedError(response.getError());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("", "Error => " + error.toString());
            }
        });
        RQSingleton.getInstance(mCtx).addToRequestQueue(pageRequest);

    }


}
