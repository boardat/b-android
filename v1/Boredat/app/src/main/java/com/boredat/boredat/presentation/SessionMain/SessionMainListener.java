package com.boredat.boredat.presentation.SessionMain;

import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.api.responses.UserResponse;

/**
 * Created by Liz on 3/17/2016.
 */
public interface SessionMainListener {
    void onReceivedUserDetails(UserResponse response);
    void onServerError(BoredatResponse.Error error);
}
