package com.boredat.boredatdroid.CreateNewPost;

/**
 * Created by Liz on 10/22/2015.
 */
public interface CreateNewPostPresenter {
    void onPost(String text, boolean anonymously, int locationId);
}
