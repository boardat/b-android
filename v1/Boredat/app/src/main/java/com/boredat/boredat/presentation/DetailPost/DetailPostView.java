package com.boredat.boredat.presentation.DetailPost;

import com.boredat.boredat.model.api.responses.Post;

import java.util.List;

/**
 * Created by Liz on 2/11/2016.
 */
public interface DetailPostView {
    void showMessage(String text);
    void showPost(Post post);

    void showLocalAgreeVote();
    void showLocalDisagreeVote();
    void showLocalNewsworthyVote();

    void showNetworkAgreeVote();
    void showNetworkDisagreeVote();
    void showNetworkNewsworthyVote();

    void showNoReplies();
    void showLoadingPostReplies();
    void hideLoadingPostReplies();
    void showPostReplies(List<Post> replies);
}
