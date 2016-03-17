package com.boredat.boredat.presentation.Login;
import com.android.volley.VolleyError;
import com.boredat.boredat.model.api.responses.BoredatResponse.Error;
import com.boredat.boredat.model.authorization.AccessToken;
import com.boredat.boredat.model.authorization.Account;

/**
 * Created by Liz on 2/17/2016.
 */
public interface LoginListener {
    void onVolleyError(VolleyError error);
    void onServerError(Error error);
    void onSuccess(Account account, AccessToken accessToken);
}
