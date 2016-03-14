package com.boredat.boredatdroid.CreateNewPost;

import com.boredat.boredatdroid.models.ServerMessage;
import com.boredat.boredatdroid.network.BoredatResponse;

/**
 * Created by Liz on 10/22/2015.
 */
public interface OnFinishedPostListener {
    void onPostSuccessListener(ServerMessage response);
    void onPostErrorListener(BoredatResponse.Error response);
}
