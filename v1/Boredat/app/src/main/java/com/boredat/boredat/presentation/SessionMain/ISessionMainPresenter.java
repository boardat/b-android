package com.boredat.boredat.presentation.SessionMain;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Liz on 3/17/2016.
 */
public interface ISessionMainPresenter {
    void start(CompositeSubscription compositeSubscription);
    void finish();
    void getUserDetails();
}
