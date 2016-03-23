package com.boredat.boredat.presentation.Compose;

/**
 * Created by Liz on 2/20/2016.
 */
public interface IComposePresenter {
    void loadViewPrefs();
    void onPost(String text, boolean isAnonymous);

    void onAnonSelected();
    void onPersonalitySelected();
}
