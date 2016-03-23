package com.boredat.boredat.presentation.Compose;

/**
 * Created by Liz on 2/20/2016.
 */
public interface ComposeView {
    void showProgress();
    void hideProgress();
    void showMessage(String text);


    void showPreferredView(String personalityName, boolean preferAnon);
    void showPreferredView();

    String getEnteredText();
    boolean isPostAnonymous();
}
