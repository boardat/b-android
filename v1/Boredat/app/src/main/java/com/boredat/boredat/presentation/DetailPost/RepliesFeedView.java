package com.boredat.boredat.presentation.DetailPost;

import com.boredat.boredat.model.api.responses.Post;

import java.util.List;

/**
 * Created by Liz on 3/21/2016.
 */
public interface RepliesFeedView {
    void showFeedMessage(String text);

    void showEmptyView();
    void hideEmptyView();

    void showFeedProgress();
    void hideFeedProgress();

    void showReplies(List<Post> replies);
}
