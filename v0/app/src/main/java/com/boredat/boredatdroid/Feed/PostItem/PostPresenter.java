package com.boredat.boredatdroid.Feed.PostItem;

/**
 * Created by Liz on 10/18/2015.
 */
public interface PostPresenter {
    void onVoteAgree(String postId);
    void onVoteDisagree(String postId);
    void onVoteNewsworthy(String postId);
}
