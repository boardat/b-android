package com.boredat.boredatdroid.Feed.PostItem;

import android.content.Context;

import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 10/18/2015.
 */
public interface PostInteractor {
    public void agree(String postId, Context mCtx, final OnVotedListener listener, UserSessionManager session);
    public void disagree(String postId, Context mCtx, final OnVotedListener listener, UserSessionManager session);
    public void newsworthy(String postId, Context mCtx, final OnVotedListener listener, UserSessionManager session);
}
