package com.boredat.boredat.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.boredat.boredat.BoredatApplication;
import com.boredat.boredat.R;
import com.boredat.boredat.adapters.RepliesAdapter;
import com.boredat.boredat.adapters.ReplySpinnerAdapter;
import com.boredat.boredat.model.api.BoredatService;
import com.boredat.boredat.model.api.UserPreferences;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.presentation.DetailPost.ComposeReplyPresenter;
import com.boredat.boredat.presentation.DetailPost.ComposeReplyView;
import com.boredat.boredat.presentation.DetailPost.DetailPostPresenter;
import com.boredat.boredat.presentation.DetailPost.DetailPostView;
import com.boredat.boredat.presentation.DetailPost.IComposeReplyPresenter;
import com.boredat.boredat.presentation.DetailPost.IDetailPostPresenter;
import com.boredat.boredat.presentation.DetailPost.IRepliesFeedPresenter;
import com.boredat.boredat.presentation.DetailPost.RepliesFeedPresenter;
import com.boredat.boredat.presentation.DetailPost.RepliesFeedView;
import com.boredat.boredat.ui.DividerItemDecoration;
import com.boredat.boredat.util.Constants;
import com.boredat.boredat.util.DateUtils;
import com.bowyer.app.fabtoolbar.FabToolbar;
import com.devspark.robototextview.widget.RobotoTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailPostActivity extends AppCompatActivity implements DetailPostView,
        RepliesFeedView, RepliesAdapter.OnReplyItemClickListener,
        ComposeReplyView, AdapterView.OnItemSelectedListener {
    // Constants
    public static String KEY_POST_ID = "postId";
    public static String KEY_FEED_ID = "feedId";

    // Member variables
    private long mPostId;
    private int mFeedId;
    private IDetailPostPresenter mDetailPostPresenter;
    private IRepliesFeedPresenter mRepliesFeedPresenter;
    private IComposeReplyPresenter mComposeReplyPresenter;

    @Inject
    BoredatService mService;
    @Inject
    UserPreferences mPrefs;

    // Toolbar
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    // Detail Post
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
    ImageButton mVoteNewsworthyButton;
    @Bind(R.id.detail_post_action_vote_agree)
    ImageButton mVoteAgreeButton;
    @Bind(R.id.detail_post_action_vote_disagree)
    ImageButton mVoteDisagreeButton;

    // Replies
    @Bind(R.id.progressHolder)
    FrameLayout progressLoadReplies;
    @Bind(R.id.emptyRepliesHolder)
    FrameLayout emptyRepliesHolder;
    @Bind(R.id.repliesHolder)
    FrameLayout repliesHolder;
    @Bind(R.id.recycler_view_replies)
    RecyclerView mRecyclerView;
    private RepliesAdapter mRepliesAdapter;
    private LinearLayoutManager mRepliesLayoutManager;

    // Compose reply
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.fabtoolbar)
    FabToolbar fabToolbar;
    @Bind(R.id.composeReplyViewHolder)
    RelativeLayout composeReplyViewHolder;
    @Bind(R.id.compose_reply_spinner)
    AppCompatSpinner composeReplySpinner;
    @Bind(R.id.compose_reply_et)
    EditText composeReplyEditText;
    @Bind(R.id.compose_reply_send_button)
    ImageButton composeReplySendButton;
    @Bind(R.id.progressBarHolder)
    FrameLayout progressBarHolder;
    ReplySpinnerAdapter mSpinnerAdapter;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    private boolean mIsPostAnonymous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_post);

        setContentView(R.layout.activity_detail_post);

        ButterKnife.bind(this);

        // Get post id
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPostId = bundle.getLong(KEY_POST_ID);
            mFeedId = bundle.getInt(KEY_FEED_ID);
        }

        setupToolbar();
        setupFab();
        fabToolbar.setFab(fab);

        getDetailPostPresenter().onLoadDetailPost(mPostId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * Detail Post View
     */
    @Override
    public void showComposeMessage(String text) {
        Toast.makeText(DetailPostActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(DetailPostActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDetailPost(Post post) {
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

         getRepliesFeedPresenter().onLoadReplies(post);
    }

    @Override
    public void showLocalAgreeVote(Post post) {
        showDetailPost(post);
    }

    @Override
    public void showLocalDisagreeVote(Post post) {
        showDetailPost(post);
    }

    @Override
    public void showLocalNewsworthyVote(Post post) {
        showDetailPost(post);
    }

    @Override
    public void showNetworkAgreeVote() {
        showComposeMessage("Voted agree!");
    }

    @Override
    public void showNetworkDisagreeVote() {
        showComposeMessage("Voted disagree!");
    }

    @Override
    public void showNetworkNewsworthyVote() {
        showComposeMessage("Voted newsworthy!");
    }

    @Override
    public void showFeedMessage(String text) {
        Toast.makeText(DetailPostActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * Replies Feed View
     */
    @Override
    public void showEmptyView() {
        emptyRepliesHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyView() {
        emptyRepliesHolder.setVisibility(View.GONE);
    }

    @Override
    public void showFeedProgress() {
        progressLoadReplies.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideFeedProgress() {
        progressLoadReplies.setVisibility(View.GONE);
    }

    @Override
    public void showReplies(List<Post> replies) {
        repliesHolder.setVisibility(View.VISIBLE);
        mRepliesLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mRepliesLayoutManager);

        mRepliesAdapter = new RepliesAdapter(this);
        mRepliesAdapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(mRepliesAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRepliesAdapter.addAll(replies);
    }

    @Override
    public void onItemClick(int position, View view) {
        Post reply = mRepliesAdapter.getItem(position);
        getRepliesFeedPresenter().onLoadDetailReply(mFeedId, reply.getPostId());
    }


    /**
     * Compose Reply View
     */
    @Override
    public void showComposeReplyView(String personalityName, boolean preferAnon) {
//        composeReplyViewHolder.setVisibility(View.VISIBLE);
//        composeReplyViewHolder.clearFocus();

        fabToolbar.expandFab();


        mIsPostAnonymous = preferAnon;

        ArrayList<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.anonymous));
        list.add(personalityName);

        setupSpinner(list);

        if (preferAnon) {
            composeReplySpinner.setSelection(0);
        } else {
            composeReplySpinner.setSelection(1);
        }

        setupSendButton();
        setupKeyboard();
    }

    @Override
    public void showComposeReplyView() {
//        composeReplyViewHolder.setVisibility(View.VISIBLE);
//        composeReplyViewHolder.clearFocus();

        fabToolbar.expandFab();

//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        composeReplySpinner.setEnabled(false);
        mIsPostAnonymous = true;

        ArrayList<String> list = new ArrayList<>();
        list.add(getResources().getString(R.string.anonymous));
        setupSpinner(list);
        setupSendButton();
        setupKeyboard();
    }


    private void setupKeyboard() {
        composeReplyEditText.requestFocus();
        showSoftKeyboard(composeReplyEditText);
    }
    private void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void setupSpinner(List<String> list) {
        mSpinnerAdapter = new ReplySpinnerAdapter(this, R.layout.reply_personality_line_item_selected, list);
        composeReplySpinner.setAdapter(mSpinnerAdapter);
        composeReplySpinner.setOnItemSelectedListener(this);
    }

    private void setupSendButton() {
        composeReplySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getComposeReplyPresenter().onReply(mPostId, getEnteredText(), isPostAnonymous());
            }
        });
    }

    @Override
    public void hideComposeReplyView() {
//        composeReplyViewHolder.setVisibility(View.GONE);
        composeReplyViewHolder.clearFocus();
        fabToolbar.slideOutFab();
        fabToolbar.slideInFab();

    }

    @Override
    public String getEnteredText() {
        return composeReplyEditText.getText().toString();
    }

    @Override
    public boolean isPostAnonymous() {
        return mIsPostAnonymous;
    }

    @Override
    public void showSendReplyProgress() {
        composeReplySendButton.setEnabled(false);
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSendReplyProgress() {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
        composeReplySendButton.setEnabled(true);
    }

    @Override
    public void showReplyFab() {
        fab.setEnabled(true);
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideReplyFab() {
        fab.setEnabled(false);
        fab.setVisibility(View.GONE);
    }

    @Override
    public void showSentReply() {
        showComposeMessage("Sent reply!");
        hideComposeReplyView();
        showReplyFab();
        getDetailPostPresenter().onLoadDetailPost(mPostId);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        String anon = getResources().getString(R.string.anonymous);

        if (item.equals(anon)) {
            mIsPostAnonymous = true;
            getComposeReplyPresenter().onAnonSelected();
        } else {
            mIsPostAnonymous = false;
            getComposeReplyPresenter().onPersonalitySelected();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
    * Helper Methods
    * */
    private IDetailPostPresenter getDetailPostPresenter() {
        if (mService == null) {
            switch(mFeedId) {
                case Constants.FEED_ID_GLOBAL:
                    BoredatApplication.get(this).getGlobalBoardComponent().inject(this);
                    break;
                default:
                    BoredatApplication.get(this).getLocalBoardComponent().inject(this);
                    break;
            }
        }

        if (mDetailPostPresenter == null) {
            mDetailPostPresenter = new DetailPostPresenter(mService, this);
        }

        return mDetailPostPresenter;
    }

    private IRepliesFeedPresenter getRepliesFeedPresenter() {
        if (mService == null) {
            switch(mFeedId) {
                case Constants.FEED_ID_GLOBAL:
                    BoredatApplication.get(this).getGlobalBoardComponent().inject(this);
                    break;
                default:
                    BoredatApplication.get(this).getLocalBoardComponent().inject(this);
                    break;
            }
        }

        if (mRepliesFeedPresenter == null) {
            mRepliesFeedPresenter = new RepliesFeedPresenter(this, mService, this);
        }

        return mRepliesFeedPresenter;
    }

    private IComposeReplyPresenter getComposeReplyPresenter() {
        if (mService == null) {
            switch(mFeedId) {
                case Constants.FEED_ID_GLOBAL:
                    BoredatApplication.get(this).getGlobalBoardComponent().inject(this);
                    break;
                default:
                    BoredatApplication.get(this).getLocalBoardComponent().inject(this);
                    break;
            }
        }

        if (mComposeReplyPresenter == null) {
            mComposeReplyPresenter = new ComposeReplyPresenter(mService, mPrefs, this);
        }

        return mComposeReplyPresenter;
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        // Setup title
        String title = String.format("Post #%d", mPostId);
        toolbar.setTitle(title);
    }

    private void setupFab() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getComposeReplyPresenter().composeReply();
            }
        });
    }


    /**
     * Set Up Detail Post
     */

    private void setUpPersonalityImage(ImageView iv, Post post) {
        if (post.isAnonymous()) {
            iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.profile_picture));
        } else {
            // TODO implement image loader
            iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.personality_thumbnail_malloc));
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


        if (post.localHasVotedAgree()) {
            numAgrees++;
        }

        if (post.localHasVotedDisagree()) {
            numDisagrees++;
        }

        if (post.localHasVotedNewsworthy()) {
            numNewsworthies++;
        }

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

    private void setUpNewsworthyButton(ImageButton button, final Post post) {
        // voted newsworthy
        if (post.hasVotedNewsworthy() || post.localHasVotedNewsworthy()) {
            // newsworthy is selected and disabled
            button.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_post_newsworthy_disabled));
            button.setEnabled(false);
        } else {
            button.setEnabled(true);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDetailPostPresenter().onVoteNewsworthy(post);
                }
            });
        }
    }


    private void setUpADButtons(ImageButton agreeButton, ImageButton disagreeButton, final Post post) {
        // voted agree
        if (post.hasVotedAgree() || post.localHasVotedAgree()) {
            // agree is selected and disabled
            agreeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_detail_post_agree_disabled_selected));
            agreeButton.setEnabled(false);

            // disagree is disabled
            disagreeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_detail_post_disagree_disabled));
            disagreeButton.setEnabled(false);
        }

        // voted disagree
        else if (post.hasVotedDisagree() || post.localHasVotedDisagree()) {
            // disagree is selected and disabled
            disagreeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_detail_post_disagree_disabled_selected));
            disagreeButton.setEnabled(false);

            // agree is disabled
            agreeButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_detail_post_agree_disabled));
            agreeButton.setEnabled(false);
        }

        // has not voted
        else {
            agreeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDetailPostPresenter().onVoteAgree(post);
                }
            });
            disagreeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDetailPostPresenter().onVoteDisagree(post);
                }
            });

        }
    }
}
