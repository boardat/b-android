package com.boredat.boredatdroid.Feed.PostItem;

import android.content.Context;
import android.util.Log;

import com.boredat.boredatdroid.models.ServerMessage;
import com.boredat.boredatdroid.network.BoredatResponse;
import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 10/18/2015.
 */
public class PostPresenterImpl implements PostPresenter, OnVotedListener {
    private final Context mCtx;
    private PostView mPostView;
    private PostInteractor mPostInteractor;
    // User Session Manager Class
    UserSessionManager sessionManager;

    public PostPresenterImpl(Context context, PostView postView) {
        this.mCtx = context;
        this.mPostView = postView;
        this.mPostInteractor = new PostInteractorImpl();
    }

    @Override
    public void onVoteAgree(String postId) {
        Log.d("", "Presenter => onVoteAgree(" + postId + ")");
        mPostInteractor.agree(postId,mCtx,this,getSessionManager());
    }

    @Override
    public void onVoteDisagree(String postId) {
        Log.d("", "Presenter => onVoteDisagree(" + postId + ")" );
        if (this.sessionManager == null) {
            this.sessionManager = new UserSessionManager(mCtx);
        }
        mPostInteractor.disagree(postId, mCtx, this, getSessionManager());
    }

    @Override
    public void onVoteNewsworthy(String postId) {
        Log.d("", "Presenter => onVoteNewsworthy(" + postId + ")" );
        mPostInteractor.newsworthy(postId, mCtx, this, getSessionManager());
    }

    // TODO update data set
    @Override
    public void onAgreeSuccess(ServerMessage response) {
        mPostView.showAgree();
    }

    @Override
    public void onDisagreeSuccess(ServerMessage response) {
        mPostView.showDisagree();
    }

    @Override
    public void onNewsworthySuccess(ServerMessage response) {
        mPostView.showNewsworthy();
    }

    @Override
    public void onVoteError(BoredatResponse.Error response) {
        mPostView.showMessage(response.getMessage());
    }

    private UserSessionManager getSessionManager() {
        if (this.sessionManager == null) {
            this.sessionManager = new UserSessionManager(mCtx);
        }
        return this.sessionManager;
    }
}
