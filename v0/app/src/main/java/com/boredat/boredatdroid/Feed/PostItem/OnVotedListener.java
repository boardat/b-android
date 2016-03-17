package com.boredat.boredatdroid.Feed.PostItem;

import com.boredat.boredatdroid.models.ServerMessage;
import com.boredat.boredatdroid.network.BoredatResponse;

/**
 * Created by Liz on 10/12/2015.
 */
public interface OnVotedListener {
    void onAgreeSuccess(ServerMessage response);
    void onDisagreeSuccess(ServerMessage response);
    void onNewsworthySuccess(ServerMessage response);
    void onVoteError(BoredatResponse.Error response);
}
