package com.boredat.boredat.presentation.SessionMain;

import android.util.Log;

import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.api.responses.UserResponse;
import com.boredat.boredat.util.Constants;

import rx.Subscriber;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Liz on 3/17/2016.
 */


public class SessionMainPresenter implements ISessionMainPresenter, SessionMainListener {
    private BoredatService mService;
    private UserPreferences mUserPrefs;
    private CompositeSubscription mCompositeSubscription;


    public SessionMainPresenter(BoredatService service, UserPreferences userPrefs) {
        this.mService = service;
        this.mUserPrefs = userPrefs;
    }

    // Lifecycle Methods
    @Override
    public void start(CompositeSubscription compositeSubscription) {
        mCompositeSubscription = compositeSubscription;
    }

    @Override
    public void finish() {
        mCompositeSubscription.unsubscribe();
    }

    // Presentation
    @Override
    public void getUserDetails() {
        Observable<UserResponse> observable = mService.getUser();
        mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<UserResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(Constants.TAG, "onCompleted getUserDetails");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onNext(UserResponse response) {
                        if (!response.isError()) {
                            onReceivedUserDetails(response);
                        } else {
                            onServerError(response.getError());
                        }
                    }
                })
        );
    }

    // Listener
    @Override
    public void onReceivedUserDetails(UserResponse response) {
        mUserPrefs.saveUserDetails(response);
    }

    @Override
    public void onServerError(BoredatResponse.Error error) {
        Log.d(Constants.TAG, "Server Error: " + error.getMessage());

    }


}
