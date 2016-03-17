package com.boredat.boredatdroid.CreateNewPost;

/**
 * Created by Liz on 10/22/2015.
 */
public interface CreateNewPostView {
    void showMessage(String message);
    public void showProgress();
    public void hideProgress();
    public void onSentPost();
}
