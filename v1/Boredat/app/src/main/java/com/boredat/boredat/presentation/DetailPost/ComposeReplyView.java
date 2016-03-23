package com.boredat.boredat.presentation.DetailPost;

/**
 * Created by Liz on 3/21/2016.
 */
public interface ComposeReplyView {
    void showComposeMessage(String text);
    void showComposeReplyView(String personalityName, boolean preferAnon);
    void showComposeReplyView();
    void hideComposeReplyView();

    String getEnteredText();
    boolean isPostAnonymous();

    void showSendReplyProgress();
    void hideSendReplyProgress();

    void showReplyFab();
    void hideReplyFab();

    void showSentReply();
}
