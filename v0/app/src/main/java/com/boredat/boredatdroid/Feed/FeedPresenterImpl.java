package com.boredat.boredatdroid.Feed;

import android.content.Context;
import android.util.Log;

import com.boredat.boredatdroid.models.Paginator;
import com.boredat.boredatdroid.models.PostFeed;
import com.boredat.boredatdroid.network.BoredatResponse;
import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 7/13/2015.
 */
public class FeedPresenterImpl implements FeedPresenter, OnLoadedFeedListener {
    public static final int PER_PAGE = 50;
    private final Context mCtx;
    private final FeedView mFeedView;
    private FeedInteractor mInteractor;

    // User Session Manager Class
    UserSessionManager sessionManager;
    Paginator mPaginator;

    public FeedPresenterImpl(Context context, FeedView v) {
        mCtx = context;
        mFeedView = v;
        mInteractor = new FeedInteractorImpl();
    }

    @Override
    public void onResume() {
        this.sessionManager = new UserSessionManager(mCtx);
        mFeedView.showProgress();
        mInteractor.loadPage(this.sessionManager.getCurrentPage(), mCtx, this, sessionManager);
    }

    @Override
    public void onLoadPage(int pageNum) {
        Log.d("", "Presenter => onLoadPage(" + pageNum + ")" );
        if (this.sessionManager == null) {
            this.sessionManager = new UserSessionManager(mCtx);
        } if (this.mPaginator == null) {
            this.mPaginator = new Paginator();
        }
        this.sessionManager.setCurrentPage(pageNum);
        this.mPaginator.setCurrentPage(pageNum);
        mFeedView.showProgress();
        mInteractor.loadPage(pageNum, mCtx, this, sessionManager);
    }

    @Override
    public void onLoadedFeedSuccess(PostFeed response) {
        Log.d("", "Presenter => onLoadedFeedSuccess, page: " + mPaginator.getCurrentPage());
        if (this.sessionManager == null) {
            this.sessionManager = new UserSessionManager(mCtx);
        } if (this.mPaginator == null) {
            this.mPaginator = new Paginator(this.sessionManager.getCurrentPage());
        }

        mFeedView.showFeedPage(response.getFeed(), this.mPaginator);
        mFeedView.hideProgress();
    }

    @Override
    public void onLoadedFeedError(BoredatResponse.Error response) {
        Log.d("", "Presenter => onLoadedFeedError" );
        mFeedView.showMessage(response.getMessage());
        this.sessionManager.logoutUser();
    }
}
