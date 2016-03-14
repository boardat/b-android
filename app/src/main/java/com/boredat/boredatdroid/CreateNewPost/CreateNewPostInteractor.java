package com.boredat.boredatdroid.CreateNewPost;

import android.content.Context;

import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 10/22/2015.
 */
public interface CreateNewPostInteractor {
    public void post(String text,
                     boolean anonymously,
                     int locationId,
                     String id,
                     Context context, OnFinishedPostListener listener, UserSessionManager session);
}
