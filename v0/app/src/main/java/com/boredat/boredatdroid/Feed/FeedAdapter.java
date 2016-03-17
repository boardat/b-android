package com.boredat.boredatdroid.Feed;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boredat.boredatdroid.Feed.PostItem.PostViewHolder;
import com.boredat.boredatdroid.R;
import com.boredat.boredatdroid.models.Paginator;
import com.boredat.boredatdroid.models.Post;
import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 8/3/2015.
 */
public class FeedAdapter extends BaseFeedAdapter<RecyclerView.ViewHolder, Paginator, Post, Paginator> {
    private FeedPresenter mPresenter;
    UserSessionManager sessionManager;

    private LayoutInflater getLayoutInflater(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View postView = inflater.inflate(R.layout.post_card_view, parent, false);
        if (mPresenter == null) {
            Log.d("","whoops!");
        }
        PostViewHolder vh = new PostViewHolder(postView);
        return vh;
    }
    /*TODO clickssss*/
    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Post post = getItem(position);
        PostViewHolder postViewHolder = (PostViewHolder) holder;
        postViewHolder.bindPost(post);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View headerView = inflater.inflate(R.layout.paginator, parent, false);
        if (sessionManager == null) {
            sessionManager = new UserSessionManager(parent.getContext());
        }
        if (mPresenter == null) {
            Log.d("","whoops!");
        }

        PaginatorViewHolder vh = new PaginatorViewHolder(headerView, new PaginatorViewHolder.PaginatorViewHolderClicks() {
            @Override
            public void onClickedFirstPage(View caller) {
                Log.d("", "onClickedFirstPage in FeedAdapter");
                mPresenter.onLoadPage(1);
            }

            @Override
            public void onClickedPrevPage(View caller) {
                Log.d("", "onClickedPrevPage in FeedAdapter");
                mPresenter.onLoadPage(sessionManager.getCurrentPage() - 1);
            }

            @Override
            public void onClickedNextPage(View caller) {
                Log.d("", "onClickedNextPage in FeedAdapter");
                mPresenter.onLoadPage(sessionManager.getCurrentPage() + 1);
            }
        });
        return vh;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = getLayoutInflater(parent);
        View footerView = inflater.inflate(R.layout.paginator, parent, false);
        if (sessionManager == null) {
            sessionManager = new UserSessionManager(parent.getContext());
        }
        if (mPresenter == null) {
            Log.d("", "whoops!");
        }
        return new PaginatorViewHolder(footerView, new PaginatorViewHolder.PaginatorViewHolderClicks() {
            @Override
            public void onClickedFirstPage(View caller) {
                Log.d("", "onClickedFirstPage in FeedAdapter");
                mPresenter.onLoadPage(1);
            }

            @Override
            public void onClickedPrevPage(View caller) {
                Log.d("", "onClickedPrevPage in FeedAdapter");
                mPresenter.onLoadPage(sessionManager.getCurrentPage() - 1);
            }

            @Override
            public void onClickedNextPage(View caller) {
                mPresenter.onLoadPage(sessionManager.getCurrentPage() + 1);
            }
        });
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        Paginator header = getHeader();
        PaginatorViewHolder headerViewHolder = (PaginatorViewHolder) holder;
        headerViewHolder.bindPaginator(header);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
        Paginator footer = getFooter();
        PaginatorViewHolder footerViewHolder = (PaginatorViewHolder) holder;
        footerViewHolder.bindPaginator(footer);
    }

    public void setPresenter(FeedPresenter presenter) {
        this.mPresenter = presenter;
    }

    public FeedPresenter getPresenter() {return this.mPresenter;}

}
