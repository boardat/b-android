package com.boredat.boredatdroid.Feed;

import com.boredat.boredatdroid.models.PostFeed;
import com.boredat.boredatdroid.network.BoredatResponse;

/**
 * Created by Liz on 7/13/2015.
 */
public interface OnLoadedFeedListener {
    void onLoadedFeedSuccess(PostFeed response);
    void onLoadedFeedError(BoredatResponse.Error response);
}
