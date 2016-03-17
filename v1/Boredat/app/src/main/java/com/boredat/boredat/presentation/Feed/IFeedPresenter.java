package com.boredat.boredat.presentation.Feed;

import android.view.View;

import com.boredat.boredat.model.api.responses.Post;

/**
 * Created by Liz on 1/10/2016.
 */
public interface IFeedPresenter {
    void onLoadPage(int page);
    void onVoteAgree(Post post);
    void onVoteDisagree(Post post);
    void onVoteNewsworthy(Post post);
    void onReply(Post post);
    void onGetPostDetails(Post post);
}
