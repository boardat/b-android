package com.boredat.boredat.presentation.Feed;

import android.view.View;

import com.boredat.boredat.model.api.responses.Post;

import java.util.List;

/**
 * Created by Liz on 1/10/2016.
 */
public interface FeedView {
    void showProgress();
    void hideProgress();
    void showEmpty();
    void showMessage(String text);
    int getFeedId();

    void showFeed(List<Post> posts);
    void showDetailPost(Post post);
    void showReplyToPost(Post post);

    void showLocalAgreeVote(Post post);
    void showLocalDisagreeVote(Post post);
    void showLocalNewsworthyVote(Post post);

    void showNetworkAgreeVote();
    void showNetworkDisagreeVote();
    void showNetworkNewsworthyVote();

}
