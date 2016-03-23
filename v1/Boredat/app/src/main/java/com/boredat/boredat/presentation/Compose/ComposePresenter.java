package com.boredat.boredat.presentation.Compose;

import android.util.Log;

import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.model.api.responses.ServerMessage;
import com.boredat.boredat.util.Constants;
import com.boredat.boredat.util.oauth.BuildOauthAuthorize;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liz on 3/20/2016.
 */
public class ComposePresenter implements IComposePresenter{
    private ComposeView mView;
    private BoredatService mService;
    private UserPreferences mPrefs;

    public ComposePresenter(ComposeView composeView, BoredatService service, UserPreferences prefs) {
        this.mView = composeView;
        this.mService = service;
        this.mPrefs = prefs;
    }

    @Override
    public void loadViewPrefs() {
        String personalityName = mPrefs.getPersonalityName();
        if (personalityName == null) {
            mView.showPreferredView();
        } else {
            mView.showPreferredView(personalityName, mPrefs.getPostAnonymouslyPref());
        }
    }

    @Override
    public void onPost(String text, boolean isAnonymous) {
        mView.showProgress();

        String paramText = BuildOauthAuthorize.encode(text);

        Observable<ServerMessage> observable = getNetworkObservable(text, isAnonymous);
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
                        mView.hideProgress();
                        if (!serverMessage.isError()) {
                            mView.showMessage("Post sent!");
                        } else {
                            mView.showMessage(serverMessage.getError().getMessage());
                        }
                    }
                });
    }

    private Observable<ServerMessage> getNetworkObservable(String text, boolean isAnonymous) {
        if (isAnonymous) {
            return mService.postToMainFeed(text);
        } else {
            return mService.postToMainFeedWithPersonality(0, text);
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
}
