package com.boredat.boredat.fragments;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.activities.ComposeReplyActivity;
import com.boredat.boredat.activities.DetailPostActivity;
import com.boredat.boredat.adapters.PaginatedFeedAdapter;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.presentation.Feed.FeedPresenter;
import com.boredat.boredat.presentation.Feed.FeedView;
import com.boredat.boredat.presentation.Feed.IFeedPresenter;
import com.boredat.boredat.ui.IconizedMenu;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements FeedView,
        PaginatedFeedAdapter.OnFirstPageClickListener,
        PaginatedFeedAdapter.OnItemClickListener,
        PaginatedFeedAdapter.OnItemReplyClickListener,
        PaginatedFeedAdapter.OnItemVoteADClickListener,
        PaginatedFeedAdapter.OnItemVoteNewsworthyClickListener,
        PaginatedFeedAdapter.OnNextPageClickListener,
        PaginatedFeedAdapter.OnPreviousPageClickListener {

    // Constants
    private static final String KEY_FEED_ID = "feedId";
    private static final String KEY_CURRENT_PAGE = "currentPage";

    // Member Variables
    private int mFeedId = -1;
    private int mCurrentPage = 1;
    private PaginatedFeedAdapter mFeedAdapter;
    private IFeedPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;

    @Inject
    BoredatService mService;

    @Bind(R.id.recycler_view_feed)
    RecyclerView mFeedRecyclerView;
    @Bind(R.id.empty)
    View mEmptyView;
    @Bind(R.id.loading)
    ProgressBar mLoadingView;


    // Constructors
    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(int feedId) {
        FeedFragment fragment = new FeedFragment();

        // supply arguments
        Bundle args = new Bundle();
        args.putInt(KEY_FEED_ID, feedId);
        args.putInt(KEY_CURRENT_PAGE, 1);
        fragment.setArguments(args);

        return fragment;
    }

    // Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mFeedId = getArguments().getInt(KEY_FEED_ID);
            mCurrentPage = getArguments().getInt(KEY_CURRENT_PAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        loadPage();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        removeListeners();
        mCurrentPage = 1;
        ButterKnife.unbind(this);
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mFeedRecyclerView.setLayoutManager(mLayoutManager);
        mFeedRecyclerView.setHasFixedSize(true);

        mFeedAdapter = new PaginatedFeedAdapter(mCurrentPage, getActivity());

        // paginator view clicks
        mFeedAdapter.setOnFirstPageClickListener(this);
        mFeedAdapter.setOnNextPageClickListener(this);
        mFeedAdapter.setOnPreviousPageClickListener(this);

        // post view clicks
        mFeedAdapter.setOnItemClickListener(this);
        mFeedAdapter.setOnItemReplyClickListener(this);
        mFeedAdapter.setOnItemVoteADClickListener(this);
        mFeedAdapter.setOnItemVoteNewsworthyClickListener(this);

        mFeedRecyclerView.setAdapter(mFeedAdapter);
        mFeedRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void removeListeners() {

    }

    // FeedAdapter Click Listeners
    @Override
    public void onFirstPageClick() {
        mCurrentPage = 1;
        loadPage();
    }

    @Override
    public void onNextPageClick() {
        mCurrentPage +=1;
        loadPage();

    }

    @Override
    public void onPreviousPageClick() {
        mCurrentPage -=1;
        loadPage();
    }

    @Override
    public void onItemClick(int position, View view) {
        Post post = mFeedAdapter.getItem(position);
        getPresenter().onGetPostDetails(post);
    }

    @Override
    public void onItemReplyClick(int position, View view) {
        Post post = mFeedAdapter.getItem(position);
        getPresenter().onReply(post);
    }

    @Override
    public void onItemVoteADClick(int position, View view) {
        final Post post = mFeedAdapter.getItem(position);
        IconizedMenu popupMenu = new IconizedMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.context_menu_vote_ad, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new IconizedMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.context_vote_agree:
                        getPresenter().onVoteAgree(post);
                        break;
                    case R.id.context_vote_disagree:
                        getPresenter().onVoteDisagree(post);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }


    @Override
    public void onItemVoteNewsworthyClick(int position, View view) {
        Post post = mFeedAdapter.getItem(position);
        getPresenter().onVoteNewsworthy(post);
    }


    // FeedView Methods
    @Override
    public void showProgress() {
        mFeedRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getFeedId() {
        return mFeedId;
    }

    @Override
    public void showFeed(List<Post> posts) {
        mFeedRecyclerView.setVisibility(View.VISIBLE);
        mFeedAdapter.setCurrentPage(mCurrentPage);
        if (posts != null) {
            if (mCurrentPage > 1) {
                mFeedAdapter.clear();
                mFeedAdapter.addHeaderPaginator();
            }
            mFeedAdapter.addAll(posts);
            mFeedAdapter.addFooterPaginator();
        }
    }

    @Override
    public void showDetailPost(Post post) {
        Intent intent = new Intent(getActivity(), DetailPostActivity.class);
        intent.putExtra(DetailPostActivity.KEY_POST_ID, post.getPostId());
        startActivity(intent);
    }

    @Override
    public void showReplyToPost(Post post) {
        Intent intent = new Intent(getActivity(), ComposeReplyActivity.class);
        intent.putExtra(ComposeReplyActivity.KEY_POST_ID, post.getPostId());
        startActivity(intent);
    }

    @Override
    public void showLocalAgreeVote(Post post) {
        // todo agree animation

        showLocalVote(post);
    }

    @Override
    public void showLocalDisagreeVote(Post post) {
        // todo disagree animation

        showLocalVote(post);

    }

    @Override
    public void showLocalNewsworthyVote(Post post) {
        // todo newsworthy animation

        showLocalVote(post);
    }

    private void showLocalVote(Post post) {
        mFeedAdapter.update(post);
    }

    @Override
    public void showNetworkAgreeVote() {
        showMessage("Voted Agree!");
    }

    @Override
    public void showNetworkDisagreeVote() {
        showMessage("Voted Disagree!");
    }

    @Override
    public void showNetworkNewsworthyVote() {
        showMessage("Voted Newsworthy!");
    }




    // Helper Methods
    private IFeedPresenter getPresenter() {
        if (getActivity() != null) {
            if (BoredatApplication.get(getActivity()).getSessionComponent() != null) {
                if (mService == null) {
                    BoredatApplication.get(getActivity()).getSessionComponent().inject(this);
                }

                if (mPresenter == null) {
                    mPresenter = new FeedPresenter(mService, this);
                }

            }
        }

        return mPresenter;
    }

    private void loadPage() {
        getPresenter().onLoadPage(mCurrentPage);
    }

}
