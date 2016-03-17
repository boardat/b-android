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
    private CompositeSubscription mCompositeSubscription;

    // Constructor
    public DetailPostPresenter(BoredatService service, DetailPostView view) {
        mService = service;
        mView = view;
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
    public void onLoadPost(long postId) {
        if (mService != null) {
            Observable<Post> observable = mService.getPost(String.valueOf(postId));
            mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
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
                                        mView.showPost(post);
                                    } else {
                                        onServerError(post.getError());
                                    }
                                }
                            })
            );
        }
    }

    @Override
    public void onLoadReplies(long postId) {
        mView.showLoadingPostReplies();

        if (mService != null) {
            Observable<PostsListResponse> observable = mService.getPostReplies(String.valueOf(postId));
            mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Subscriber<PostsListResponse>() {
                                @Override
                                public void onCompleted() {
                                    Log.d(Constants.TAG, "onCompleted loadReplies");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(PostsListResponse postsListResponse) {
                                    mView.hideLoadingPostReplies();
                                    if (!postsListResponse.isError()) {
                                        mView.showPostReplies(postsListResponse.getPostsList());
                                    } else {
                                        onServerError(postsListResponse.getError());
                                    }
                                }
                            })
            );
        }
    }

    @Override
    public void onVoteAgree(final Post post) {
        mView.showLocalAgreeVote();

        if (mService != null) {
            Observable<ServerMessage> observable = mService.postAgree(String.valueOf(post.getPostId()));
            mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
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
                            })
            );
        }
    }

    @Override
    public void onVoteDisagree(Post post) {
        mView.showLocalDisagreeVote();

        if (mService != null) {
            Observable<ServerMessage> observable = mService.postDisagree(String.valueOf(post.getPostId()));
            mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
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
                            })
            );
        }
    }

    @Override
    public void onVoteNewsworthy(Post post) {
        mView.showLocalNewsworthyVote();

        Observable<ServerMessage> observable = mService.postNewsworthy(String.valueOf(post.getPostId()));
        mCompositeSubscription.add(observable.observeOn(AndroidSchedulers.mainThread())
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
                        })
        );
    }

    // Listeners
    @Override
    public void onServerError(BoredatResponse.Error error) {
        mView.showMessage(error.getMessage());
    }
}
