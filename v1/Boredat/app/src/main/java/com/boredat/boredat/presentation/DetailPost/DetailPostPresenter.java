package com.boredat.boredat.presentation.DetailPost;

import android.util.Log;

import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.model.api.responses.PostsListResponse;
import com.boredat.boredat.model.api.responses.ServerMessage;
import com.boredat.boredat.util.Constants;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Liz on 2/15/2016.
 */
public class DetailPostPresenter implements IDetailPostPresenter, DetailPostListener {
    // Member Variables
    private final DetailPostView mView;
    private BoredatService mService;

    // Constructor
    public DetailPostPresenter(BoredatService service, DetailPostView view) {
        mService = service;
        mView = view;
    }

    // Presentation
    @Override
    public void onLoadDetailPost(long postId) {
        if (mService != null) {
            Observable<Post> observable = mService.getPost(String.valueOf(postId));
            observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<Post>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(Constants.TAG, "onCompleted loadPost");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(Post post) {
                                    if (!post.isError()) {
                                        mView.showDetailPost(post);
                                    } else {
                                        onServerError(post.getError());
                                    }
                                }
                            }
            );
        }
    }


    @Override
    public void onVoteAgree(final Post post) {
        post.setLocalHasVotedAgree(true);
        mView.showLocalAgreeVote(post);

        if (mService != null) {
            Observable<ServerMessage> observable = mService.postAgree(String.valueOf(post.getPostId()));
            observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<ServerMessage>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(Constants.TAG, "onCompleted voteAgree");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(ServerMessage serverMessage) {
                                    if (!serverMessage.isError()) {
                                        mView.showNetworkAgreeVote();
                                    } else {
                                        onServerError(serverMessage.getError());
                                    }
                                }
                            }
            );
        }
    }

    @Override
    public void onVoteDisagree(Post post) {
        post.setLocalHasVotedDisagree(true);
        mView.showLocalDisagreeVote(post);

        if (mService != null) {
            Observable<ServerMessage> observable = mService.postDisagree(String.valueOf(post.getPostId()));
            observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<ServerMessage>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(Constants.TAG, "onCompleted voteDisagree");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(ServerMessage serverMessage) {
                                    if (!serverMessage.isError()) {
                                        mView.showNetworkDisagreeVote();
                                    } else {
                                        onServerError(serverMessage.getError());
                                    }
                                }
                            }
            );
        }
    }

    @Override
    public void onVoteNewsworthy(Post post) {
        post.setLocalHasVotedNewsworthy(true);
        mView.showLocalNewsworthyVote(post);

        Observable<ServerMessage> observable = mService.postNewsworthy(String.valueOf(post.getPostId()));
        observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<ServerMessage>() {
                            @Override
                            public void onCompleted() {
                                Log.d(Constants.TAG, "onCompleted voteNewsworthy");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(ServerMessage serverMessage) {
                                if (!serverMessage.isError()) {
                                    mView.showNetworkNewsworthyVote();
                                } else {
                                    onServerError(serverMessage.getError());
                                }
                            }
                        }
        );
    }

    // Listeners
    @Override
    public void onServerError(BoredatResponse.Error error) {
        mView.showMessage(error.getMessage());
    }
}
