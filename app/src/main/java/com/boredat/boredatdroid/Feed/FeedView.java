package com.boredat.boredatdroid.Feed;

import com.boredat.boredatdroid.models.Paginator;
import com.boredat.boredatdroid.models.Post;

import java.util.List;

/**
 * Created by Liz on 7/13/2015.
 */
public interface FeedView {
    public void showProgress();

    public void hideProgress();

    public void showFeedPage(List<Post> posts, Paginator paginator);

    public void showMessage(String message);


}
