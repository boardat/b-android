package com.boredat.boredat.presentation.Feed;

import android.util.Log;
import android.view.View;

import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.responses.BoredatResponse;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.model.api.responses.PostsListResponse;
import com.boredat.boredat.model.api.responses.ServerMessage;
import com.boredat.boredat.util.Constants;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Liz on 1/10/2016.
 */
public class FeedPresenter implements IFeedPresenter, FeedListener {
    private final FeedView mView;
    private BoredatService mService;

    public FeedPresenter(BoredatService service, FeedView view) {
        mView = view;
        mService = service;
    }


    @Override
    public void onLoadPage(int page) {
        mView.showProgress();

        if (mService != null) {
            if (page == 1) {
                loadFirstPage();
            } else {
                Log.d(Constants.TAG, "=> loadPage(" + String.valueOf(page) + ")");

                Observable<PostsListResponse> observable = getNetworkPageObservable(page);
                observable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(new Subscriber<PostsListResponse>() {
                            @Override
                            public void onCompleted() {
                                Log.d(Constants.TAG, "onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(PostsListResponse postsListResponse) {
                                mView.hideProgress();
                                if (!postsListResponse.isError()) {
                                    mView.showFeed(postsListResponse.getPostsList());
                                } else {
                                    mView.showMessage(postsListResponse.getError().getMessage());
                                }
                            }
                        });
            }
        }
    }

    private void loadFirstPage() {
        Log.d(Constants.TAG, "=> loadFirstPage");

        Observable<PostsListResponse> observable = getNetworkFirstPageObservable();
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<PostsListResponse>() {
                    @Override
                    public void onCompleted() {
                        Log.d(Constants.TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(PostsListResponse postsListResponse) {
                        mView.hideProgress();
                        if (!postsListResponse.isError()) {
                            onGetFeedSuccess(postsListResponse.getPostsList());
                        } else {
                            onServerError(postsListResponse.getError());
                        }
                    }
                });
    }

    // todo update with other feed ids
    private Observable<PostsListResponse> getNetworkFirstPageObservable() {
        switch(mView.getFeedId()) {
            case Constants.FEED_ID_LOCAL:
                return mService.getPosts();
            case Constants.FEED_ID_WEEKS_BEST:
                return mService.getWeeksBest();
            case Constants.FEED_ID_WEEKS_WORST:
                return mService.getWeeksWorst();
            default:
                return mService.getPosts();
        }
    }

    // todo update with other feed ids
    private Observable<PostsListResponse> getNetworkPageObservable(int pageNum) {
        switch(mView.getFeedId()) {
            case Constants.FEED_ID_LOCAL:
                return mService.getPostsWithPage(pageNum);
            case Constants.FEED_ID_WEEKS_BEST:
                return mService.getWeeksBestWithPage(pageNum);
            case Constants.FEED_ID_WEEKS_WORST:
                return mService.getWeeksWorstWithPage(pageNum);
            default:
                return mService.getPostsWithPage(pageNum);
        }
    }

    @Override
    public void onVoteAgree(final Post post) {
        post.setLocalHasVotedAgree(true);
        mView.showLocalAgreeVote(post);

        Observable<ServerMessage> observable = mService.postAgree(String.valueOf(post.getPostId()));
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
                        if (!serverMessage.isError()) {
                            onNetworkAgree(post);
                        } else {
                            onServerError(serverMessage.getError());
                        }
                    }
                });


    }

    @Override
    public void onVoteDisagree(final Post post) {
        post.setLocalHasVotedDisagree(true);
        mView.showLocalDisagreeVote(post);

        Observable<ServerMessage> observable = mService.postDisagree(String.valueOf(post.getPostId()));
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
                        if (!serverMessage.isError()) {
                            onNetworkDisagree(post);
                        } else {
                            onServerError(serverMessage.getError());
                        }
                    }
                });
    }

    @Override
    public void onVoteNewsworthy(final Post post) {
        post.setLocalHasVotedNewsworthy(true);
        mView.showLocalNewsworthyVote(post);

        Observable<ServerMessage> observable = mService.postNewsworthy(String.valueOf(post.getPostId()));
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
                        if (!serverMessage.isError()) {
                            onNetworkNewsworhty(post);
                        } else {
                            onServerError(serverMessage.getError());
                        }
                    }
                });
    }

    @Override
    public void onReply(Post post) {
        mView.showReplyToPost(post);
    }

    @Override
    public void onGetPostDetails(Post post) {
        mView.showDetailPost(post);
    }

    // TODO better error handling
    @Override
    public void onServerError(BoredatResponse.Error error) {
        mView.showEmpty();
        mView.showMessage(error.getMessage());
    }

    @Override
    public void onGetFeedSuccess(List<Post> data) {
        mView.hideProgress();
        mView.showFeed(data);
    }

    @Override
    public void onNetworkAgree(Post post) {
        mView.showNetworkAgreeVote();
    }

    @Override
    public void onNetworkDisagree(Post post) {
        mView.showNetworkDisagreeVote();
    }

    @Override
    public void onNetworkNewsworhty(Post post) {
        mView.showNetworkNewsworthyVote();
    }
}
