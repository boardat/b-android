package com.boredat.boredat.presentation.DetailPost;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.boredat.boredat.activities.DetailPostActivity;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.model.api.responses.PostsListResponse;
import com.boredat.boredat.util.Constants;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liz on 3/21/2016.
 */
public class RepliesFeedPresenter implements IRepliesFeedPresenter, DetailPostListener{
    private Context mCtx;
    private BoredatService mService;
    private RepliesFeedView mView;

    public RepliesFeedPresenter(Context context, BoredatService service, RepliesFeedView view) {
        mCtx = context;
        mService = service;
        mView = view;
    }

    @Override
    public void onLoadReplies(Post post) {
        if (post.getPostTotalReplies() == 0) {
            mView.showEmptyView();
        } else {
            mView.hideEmptyView();
            mView.showFeedProgress();
            long postId = post.getPostId();

            if (mService != null) {
                Observable<PostsListResponse> observable = mService.getPostReplies(String.valueOf(postId));
                observable.observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.newThread())
                                .subscribe(new Subscriber<PostsListResponse>() {
                                               @Override
                                               public void onCompleted() {
                                                   Log.d(Constants.TAG, "onCompleted loadReplies");
                                               }

                                               @Override
                                               public void onError(Throwable e) {
                                                   e.printStackTrace();
                                               }

                                               @Override
                                               public void onNext(PostsListResponse postsListResponse) {
                                                   mView.hideFeedProgress();
                                                   if (!postsListResponse.isError()) {
                                                       mView.showReplies(postsListResponse.getPostsList());
                                                   } else {
                                                       onServerError(postsListResponse.getError());
                                                   }
                                               }
                                           }
                                );
            }
        }
    }

    @Override
    public void onLoadDetailReply(int feedId, long postId) {
        Intent intent = new Intent(mCtx, DetailPostActivity.class);
        intent.putExtra(DetailPostActivity.KEY_FEED_ID, feedId);
        intent.putExtra(DetailPostActivity.KEY_POST_ID, postId);
        mCtx.startActivity(intent);
    }

    @Override
    public void onServerError(BoredatResponse.Error error) {
        mView.showFeedMessage(error.getMessage());
    }
}
