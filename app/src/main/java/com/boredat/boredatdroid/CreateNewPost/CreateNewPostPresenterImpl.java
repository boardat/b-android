package com.boredat.boredatdroid.CreateNewPost;

import android.content.Context;
import android.util.Log;

import com.boredat.boredatdroid.models.ServerMessage;
import com.boredat.boredatdroid.network.BoredatResponse;
import com.boredat.boredatdroid.network.UserSessionManager;
import com.boredat.boredatdroid.util.Constants;

/**
 * Created by Liz on 10/22/2015.
 */
public class CreateNewPostPresenterImpl implements CreateNewPostPresenter,OnFinishedPostListener {
    private Context mCtx;
    private CreateNewPostView mView;
    private CreateNewPostInteractor mInteractor;
    UserSessionManager sessionManager;

    public CreateNewPostPresenterImpl(Context context, CreateNewPostView v) {
        mCtx = context;
        mView = v;
        mInteractor = new CreateNewPostInteractorImpl();
    }

    @Override
    public void onPost(String text, boolean anonymously, int locationId) {
        mView.showProgress();
        mInteractor.post(text,anonymously,locationId,null,mCtx,this,getSessionManager());
    }

    @Override
    public void onPostSuccessListener(ServerMessage response) {
        mView.hideProgress();
        mView.showMessage("post success");


    }

    @Override
    public void onPostErrorListener(BoredatResponse.Error response) {
        mView.hideProgress();
        mView.showMessage(response.getMessage());
        Log.d(Constants.TAG,"onPostErrorListener => " + response.getMessage());
    }

    private UserSessionManager getSessionManager() {
        if (this.sessionManager == null) {
            this.sessionManager = new UserSessionManager(mCtx);
        }
        return this.sessionManager;
    }
}
