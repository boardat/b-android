package com.boredat.boredat.presentation.DetailPost;

/**
 * Created by Liz on 3/21/2016.
 */
public interface IComposeReplyPresenter {
    void composeReply();
//    void cancelComposeReply();

    void onAnonSelected();
    void onPersonalitySelected();

    void onReply(long postId, String text, boolean isAnonymous);
}
