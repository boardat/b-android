package com.boredat.boredat.presentation.DetailPost;

import android.util.Log;

import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.api.responses.ServerMessage;
import com.boredat.boredat.util.Constants;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liz on 3/21/2016.
 */
public class ComposeReplyPresenter implements IComposeReplyPresenter, DetailPostListener {
    BoredatService mService;
    UserPreferences mPrefs;
    ComposeReplyView mView;

    public ComposeReplyPresenter(BoredatService service, UserPreferences prefs, ComposeReplyView view) {
        mService = service;
        mPrefs = prefs;
        mView = view;
    }

    @Override
    public void composeReply() {
//        mView.hideReplyFab();
        String personalityName = mPrefs.getPersonalityName();
        if (personalityName == null) {
            mView.showComposeReplyView();
        } else {
            mView.showComposeReplyView(personalityName, mPrefs.getPostAnonymouslyPref());
        }
    }

    @Override
    public void onAnonSelected() {
        mPrefs.savePostAnonymouslyPref(true);
    }

    @Override
    public void onPersonalitySelected() {
        mPrefs.savePostAnonymouslyPref(false);
    }

    @Override
    public void onReply(long postId, String text, boolean isAnonymous) {
        mView.showSendReplyProgress();

        Observable<ServerMessage> observable = getNetworkObservable(postId, text, isAnonymous);
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ServerMessage>() {
                    @Override
                    public void onCompleted() {
                        Log.d(Constants.TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ServerMessage serverMessage) {
                        mView.hideSendReplyProgress();
                        if (!serverMessage.isError()) {
                            mView.showSentReply();
                            mView.hideComposeReplyView();
                        } else {
                            mView.showComposeMessage(serverMessage.getError().getMessage());
                        }
                    }
                });
    }

    private Observable<ServerMessage> getNetworkObservable(long postId, String text, boolean isAnonymous) {
        String id = String.valueOf(postId);

        if (isAnonymous) {
            return mService.postReply(id, text);
        } else {
            return mService.postReplyWithPersonality(id, 0, text);
        }
    }

    @Override
    public void onServerError(BoredatResponse.Error error) {
        mView.showComposeMessage(error.getMessage());
    }
}
