package com.boredat.boredat.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.activities.DetailPostActivity;
import com.boredat.boredat.adapters.RepliesAdapter;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.presentation.DetailPost.DetailPostView;
import com.boredat.boredat.presentation.DetailPost.IDetailPostPresenter;
import com.boredat.boredat.presentation.DetailPost.DetailPostPresenter;
import com.boredat.boredat.ui.DividerItemDecoration;
import com.boredat.boredat.ui.NewsworthyButton;
import com.boredat.boredat.util.DateUtils;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPostFragment extends Fragment implements DetailPostView, RepliesAdapter.OnItemClickListener {
    // Constants
    public static final String KEY_POST_ID = "postId";

    // Member Variables
    private long mPostId;
    private IDetailPostPresenter mPresenter;

    private boolean mHasReplies = false;
    private RepliesAdapter mRepliesAdapter;
    private LinearLayoutManager mRepliesLayoutManager;

    @Inject
    BoredatService mService;

    @Bind(R.id.recycler_view_replies)
    RecyclerView mRepliesRecyclerView;
    @Bind(R.id.empty)
    View mEmptyRepliesView;
    @Bind(R.id.loading)
    ProgressBar mLoadingRepliesView;

    @Bind(R.id.detail_post_personality_image)
    ImageView mPersonalityImageView;
    @Bind(R.id.detail_post_personality_name)
    RobotoTextView mPersonalityNameTextView;
    @Bind(R.id.detail_post_personality_timestamp_sep)
    RobotoTextView mPersonalityTimestampSepTextView;
    @Bind(R.id.detail_post_timestamp)
    RobotoTextView mTimestampTextView;
    @Bind(R.id.detail_post_content)
    RobotoTextView mContentTextView;
    @Bind(R.id.detail_post_adn)
    LinearLayout mADNLinearLayout;
    @Bind(R.id.detail_post_adn_num_agrees)
    RobotoTextView mNumAgreesTextView;
    @Bind(R.id.detail_post_adn_agrees)
    ImageView mAgreesImageView;
    @Bind(R.id.detail_post_adn_num_disagrees)
    RobotoTextView mNumDisagreesTextView;
    @Bind(R.id.detail_post_adn_disagrees)
    ImageView mDisagreesImageView;
    @Bind(R.id.detail_post_adn_num_newsworthies)
    RobotoTextView mNumNewsworthiesTextView;
    @Bind(R.id.detail_post_adn_newsworthies)
    ImageView mNewsworthiesImageView;
    @Bind(R.id.detail_post_action_newsworthy)
    NewsworthyButton mVoteNewsworthyButton;
    @Bind(R.id.detail_post_action_vote_agree)
    ImageButton mVoteAgreeButton;
    @Bind(R.id.detail_post_action_vote_disagree)
    ImageButton mVoteDisagreeButton;


    // Constructors
    public DetailPostFragment() {
        // Required empty public constructor
    }

    public static DetailPostFragment newInstance(long postId) {
        DetailPostFragment fragment = new DetailPostFragment();

        // supply arguments
        Bundle args = new Bundle();
        args.putLong(KEY_POST_ID, postId);
        fragment.setArguments(args);

        return fragment;
    }

    // Lifecycle Methods
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mPostId = getArguments().getLong(KEY_POST_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // init presenter
        getPresenter().start(new CompositeSubscription());

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_post, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRepliesLayoutManager = new LinearLayoutManager(getActivity());
        mRepliesRecyclerView.setLayoutManager(mRepliesLayoutManager);

        mRepliesAdapter = new RepliesAdapter(getActivity());
        mRepliesAdapter.setOnItemClickListener(this);

        mRepliesRecyclerView.setAdapter(mRepliesAdapter);
        mRepliesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        mRepliesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        loadPost();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        getPresenter().finish();
    }

    // Adapter methods

    @Override
    public void onItemClick(int position, View view) {
        Post post = mRepliesAdapter.getItem(position);

        if (post != null) {
            long id = post.getPostId();
            String s = "%d detail view";
            String text = String.format(s, id);
            showMessage(text);

            // navigate to postdetailactivity
            Intent intent = new Intent(getActivity(), DetailPostActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong(DetailPostActivity.KEY_POST_ID, id);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            showMessage("post is null :'(");
        }
    }


    // DetailPostView Methods

    @Override
    public void showNoReplies() {
        mRepliesRecyclerView.setVisibility(View.GONE);
        mEmptyRepliesView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showPost(Post post) {
        setUpPersonalityImage(mPersonalityImageView, post);
        setUpPersonalityName(mPersonalityNameTextView, post);
        setUpTimestampSep(mPersonalityTimestampSepTextView, post);
        setUpTimestamp(mTimestampTextView, post);
        setUpPostText(mContentTextView, post);
        setUpADN(mADNLinearLayout, mNumAgreesTextView, mAgreesImageView,
                mNumDisagreesTextView, mDisagreesImageView,
                mNumNewsworthiesTextView, mNewsworthiesImageView, post);
        setUpNewsworthyButton(mVoteNewsworthyButton, post);
        setUpADButtons(mVoteAgreeButton, mVoteDisagreeButton, post);

        if (post.getPostTotalReplies() > 0) {
            loadPostReplies();
        } else {
            showNoReplies();
        }
    }

    @Override
    public void showLocalAgreeVote() {
        showMessage("local agree vote");
        mVoteAgreeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_post_agree_selected));
        mVoteAgreeButton.setEnabled(false);
        mVoteDisagreeButton.setEnabled(false);
    }

    @Override
    public void showLocalDisagreeVote() {
        showMessage("local disagree vote");
        mVoteDisagreeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_post_disagree_selected));
        mVoteDisagreeButton.setEnabled(false);
        mVoteAgreeButton.setEnabled(false);
    }

    @Override
    public void showLocalNewsworthyVote() {
        showMessage("local newsworthy vote");
        mVoteNewsworthyButton.setHasVotedNewsworthy(true);
    }

    @Override
    public void showNetworkAgreeVote() {
        showMessage("Uploaded agree!");
    }

    @Override
    public void showNetworkDisagreeVote() {
        showMessage("Uploaded disagree!");
    }

    @Override
    public void showNetworkNewsworthyVote() {
        showMessage("Uploaded newsworthy!");
    }

    @Override
    public void showLoadingPostReplies() {
        mRepliesRecyclerView.setVisibility(View.GONE);
        mLoadingRepliesView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingPostReplies() {
        mRepliesRecyclerView.setVisibility(View.VISIBLE);
        mLoadingRepliesView.setVisibility(View.GONE);
    }

    @Override
    public void showPostReplies(List<Post> replies) {
        if (mRepliesAdapter.getItemCount() > 0) {
            mRepliesAdapter.clear();
        }
        mRepliesAdapter.addAll(replies);
    }


    // Helper Methods

    private void loadPost() {
        getPresenter().onLoadPost(mPostId);
    }

    private void loadPostReplies() {
        getPresenter().onLoadReplies(mPostId);
    }

    private IDetailPostPresenter getPresenter() {
        if (mService == null) {
            BoredatApplication.get(getActivity()).getSessionComponent().inject(this);
        }

        if (mPresenter == null) {
            mPresenter = new DetailPostPresenter(mService, this);
        }

        return mPresenter;
    }

    // Set Up Post

    private void setUpPersonalityImage(ImageView iv, Post post) {
        if (post.isAnonymous()) {
            iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.profile_picture));
        } else {
            // TODO implement image loader
            iv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.personality_thumbnail_malloc));
        }
    }

    private void setUpPersonalityName(RobotoTextView rtv, Post post) {
        if (post.isAnonymous()) {
            rtv.setVisibility(View.GONE);
        } else {
            rtv.setVisibility(View.VISIBLE);
            rtv.setText(post.getScreennameName());
        }
    }

    private void setUpTimestampSep(RobotoTextView rtv, Post post) {
        if (post.isAnonymous()) {
            rtv.setVisibility(View.GONE);
        } else {
            rtv.setVisibility(View.VISIBLE);
        }
    }

    private void setUpTimestamp(RobotoTextView rtv, Post post) {
        rtv.setText(DateUtils.getFormattedTimestampString(post.getPostCreated()));
    }

    private void setUpPostText(RobotoTextView rtv, Post post) {
        rtv.setText(post.getPostText());
    }

    private void setUpADN(LinearLayout adn_LL, RobotoTextView agrees_rtv, ImageView agrees_iv,
                          RobotoTextView disagrees_rtv, ImageView disagrees_iv,
                          RobotoTextView newsworthies_rtv, ImageView newsworthies_iv, Post post) {

        int numAgrees = post.getPostTotalAgrees();
        int numDisagrees = post.getPostTotalDisagrees();
        int numNewsworthies = post.getPostTotalNewsworthies();

        // any votes
        if (numAgrees+numDisagrees+numNewsworthies > 0) {
            adn_LL.setVisibility(View.VISIBLE);
            setUpAgrees(agrees_rtv, agrees_iv, numAgrees);
            setUpDisagrees(disagrees_rtv, disagrees_iv, numDisagrees);
            setUpNewsworthies(newsworthies_rtv, newsworthies_iv, numNewsworthies);
        } else {
            adn_LL.setVisibility(View.GONE);
        }

    }

    private void setUpAgrees(RobotoTextView votes_rtv, ImageView votes_iv, int numVotes) {
        if (numVotes > 0) {
            votes_rtv.setVisibility(View.VISIBLE);
            String voteCount = String.format("%d agree", numVotes);
            votes_rtv.setText(voteCount);
            votes_iv.setVisibility(View.VISIBLE);
        } else {
            votes_rtv.setVisibility(View.GONE);
            votes_iv.setVisibility(View.GONE);
        }
    }

    private void setUpDisagrees(RobotoTextView votes_rtv, ImageView votes_iv, int numVotes) {
        if (numVotes > 0) {
            votes_rtv.setVisibility(View.VISIBLE);
            String voteCount = String.format("%d disagree", numVotes);
            votes_rtv.setText(voteCount);
            votes_iv.setVisibility(View.VISIBLE);
        } else {
            votes_rtv.setVisibility(View.GONE);
            votes_iv.setVisibility(View.GONE);
        }
    }

    private void setUpNewsworthies(RobotoTextView votes_rtv, ImageView votes_iv, int numVotes) {
        if (numVotes > 0) {
            votes_rtv.setVisibility(View.VISIBLE);
            String voteCount = String.format("%d", numVotes);
            votes_rtv.setText(voteCount);
            votes_iv.setVisibility(View.VISIBLE);
        } else {
            votes_rtv.setVisibility(View.GONE);
            votes_iv.setVisibility(View.GONE);
        }
    }

    private void setUpNewsworthyButton(NewsworthyButton nb, final Post post) {
        // todo fix this lol
        nb.setHasVotedNewsworthy(post.hasVotedNewsworthy());

        nb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onVoteNewsworthy(post);
            }
        });
    }


    private void setUpADButtons(ImageButton agreeButton, ImageButton disagreeButton, final Post post) {
        if (post.hasVotedAgree()) {
            agreeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_post_agree_selected));
            agreeButton.setEnabled(false);
            disagreeButton.setEnabled(false);
        } else if (post.hasVotedDisagree()) {
            disagreeButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_post_disagree_selected));
            agreeButton.setEnabled(false);
            disagreeButton.setEnabled(false);
        } else {
            agreeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().onVoteAgree(post);
                }
            });
            disagreeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().onVoteDisagree(post);
                }
            });

        }
    }
}
