package com.boredat.boredat.presentation.DetailPost;

import com.boredat.boredat.model.api.responses.BoredatResponse.Error;

/**
 * Created by Liz on 2/11/2016.
 */
public interface DetailPostListener {
    void onServerError(Error error);
}
