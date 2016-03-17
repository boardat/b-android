package com.boredat.boredat.presentation.DetailPost;

import com.boredat.boredat.model.api.responses.Post;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Liz on 2/11/2016.
 */
public interface IDetailPostPresenter {
    void start(CompositeSubscription compositeSubscription);
    void onLoadPost(long postId);
    void onLoadReplies(long postId);
    void onVoteAgree(Post post);
    void onVoteDisagree(Post post);
    void onVoteNewsworthy(Post post);
    void finish();
}
