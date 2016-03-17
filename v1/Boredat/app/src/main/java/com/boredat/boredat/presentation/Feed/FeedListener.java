package com.boredat.boredat.presentation.Feed;

import android.view.View;

import com.boredat.boredat.model.api.responses.BoredatResponse.Error;
import com.boredat.boredat.model.api.responses.Post;

import java.util.List;

/**
 * Created by Liz on 1/10/2016.
 */
public interface FeedListener {
    void onServerError(Error error);

    void onGetFeedSuccess(List<Post> data);
    void onNetworkAgree(Post post);
    void onNetworkDisagree(Post post);
    void onNetworkNewsworhty(Post post);
}
