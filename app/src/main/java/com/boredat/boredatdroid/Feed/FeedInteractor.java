package com.boredat.boredatdroid.Feed;

import android.content.Context;

import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 7/13/2015.
 */
public interface FeedInteractor {
    public void loadPage(int page, Context ctx, OnLoadedFeedListener listener, UserSessionManager session);
}
