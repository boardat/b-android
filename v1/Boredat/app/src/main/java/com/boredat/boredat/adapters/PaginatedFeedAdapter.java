package com.boredat.boredat.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boredat.boredat.R;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.util.DateUtils;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Liz on 3/15/2016.
 */
public class PaginatedFeedAdapter extends FeedAdapter {
    // Constants
    public static final int HEADER = 0;
    public static final int ITEM = 1;
    public static final int FOOTER = 2;

    // Member Variables
    private int mCurrentPage;
    private Context mCtx;
    private boolean mIsHeaderPaginatorAdded = false;
    private boolean mIsFooterPaginatorAdded = false;
    private OnItemClickListener mOnItemClickListener;
    private OnItemReplyClickListener mOnItemReplyClickListener;
    private OnItemVoteNewsworthyClickListener mOnItemVoteNewsworthyClickListener;
    private OnItemVoteADClickListener mOnItemVoteADClickListener;
    private OnNextPageClickListener mOnNextPageClickListener;
    private OnPreviousPageClickListener mOnPreviousPageClickListener;
    private OnFirstPageClickListener mOnFirstPageClickListener;

    // Post Interfaces
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
    public interface OnItemReplyClickListener {
        void onItemReplyClick(int position, View view);
    }
    public interface OnItemVoteNewsworthyClickListener {
        void onItemVoteNewsworthyClick(int position, View view);
    }
    public interface OnItemVoteADClickListener {
        void onItemVoteADClick(int position, View view);
    }

    // Paginator Interfaces
    public interface OnNextPageClickListener {
        void onNextPageClick();
    }
    public interface OnPreviousPageClickListener {
        void onPreviousPageClick();
    }
    public interface OnFirstPageClickListener {
        void onFirstPageClick();
    }

    // Constructors
    public PaginatedFeedAdapter(int currentPage, Context context) {
        super();
        mCurrentPage = currentPage;
        mCtx = context;
    }

    // FeedAdapter Methods
    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        switch(viewType) {
            case HEADER:
                return createPaginatorViewHolder(parent);
            case ITEM:
                return createPostViewHolder(parent);
            case FOOTER:
                return createPaginatorViewHolder(parent);
            default:
                return null;
        }
    }

    @Override
    protected void bind(RecyclerView.ViewHolder viewHolder, int position) {
        switch(getItemViewType(position)) {
            case HEADER:
                bindPaginatorViewHolder(viewHolder);
                break;
            case ITEM:
                bindPostViewHolder(viewHolder, position);
                break;
            case FOOTER:
                bindPaginatorViewHolder(viewHolder);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (mCurrentPage > 1))
            return HEADER;
        else
            return (position == getItemCount()-1) ? FOOTER : ITEM;
    }

    // public methods
    public void addHeaderPaginator() {
        if (!mIsHeaderPaginatorAdded) {
            mIsHeaderPaginatorAdded = true;
            addPost(new Post());
        }
    }

    public void addFooterPaginator() {
        if (!mIsFooterPaginatorAdded) {
            mIsFooterPaginatorAdded = true;
            addPost(new Post());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemReplyClickListener(OnItemReplyClickListener onItemReplyClickListener) {
        this.mOnItemReplyClickListener = onItemReplyClickListener;
    }

    public void setOnItemVoteNewsworthyClickListener(OnItemVoteNewsworthyClickListener onItemVoteNewsworthyClickListener)
    {
        this.mOnItemVoteNewsworthyClickListener = onItemVoteNewsworthyClickListener;
    }

    public void setOnItemVoteADClickListener(OnItemVoteADClickListener onItemVoteADClickListener) {
        this.mOnItemVoteADClickListener = onItemVoteADClickListener;
    }

    public void setOnFirstPageClickListener(OnFirstPageClickListener onFirstPageClickListener) {
        this.mOnFirstPageClickListener = onFirstPageClickListener;
    }

    public void setOnPreviousPageClickListener(OnPreviousPageClickListener onPreviousPageClickListener) {
        this.mOnPreviousPageClickListener = onPreviousPageClickListener;
    }

    public void setOnNextPageClickListener(OnNextPageClickListener onNextPageClickListener) {
        this.mOnNextPageClickListener = onNextPageClickListener;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }


    /** ------------
     *  VIEW HOLDERS
     *  ------------ */
    private RecyclerView.ViewHolder createPaginatorViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_paginator, parent, false);
        final PaginatorViewHolder holder = new PaginatorViewHolder(view);

        holder.mFirstPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnFirstPageClickListener != null) {
                    mOnFirstPageClickListener.onFirstPageClick();
                }
            }
        });

        holder.mPreviousPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPreviousPageClickListener != null) {
                    mOnPreviousPageClickListener.onPreviousPageClick();
                }
            }
        });

        holder.mNextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNextPageClickListener != null) {
                    mOnNextPageClickListener.onNextPageClick();
                }
            }
        });


        return holder;
    }

    private RecyclerView.ViewHolder createPostViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_post_item, parent, false);
        final PostViewHolder holder = new PostViewHolder(view);

        // set click listeners
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(adapterPos, holder.mItemView);
                    }
                }
            }
        });

        holder.mReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (mOnItemReplyClickListener != null) {
                        mOnItemReplyClickListener.onItemReplyClick(adapterPos, holder.mItemView);
                    }
                }
            }
        });

        holder.mVoteNewsworthyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (mOnItemVoteNewsworthyClickListener != null) {
                        mOnItemVoteNewsworthyClickListener.onItemVoteNewsworthyClick(adapterPos, holder.mItemView);
                    }
                }
            }
        });

        holder.mVoteADButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (mOnItemVoteADClickListener != null) {
                        mOnItemVoteADClickListener.onItemVoteADClick(adapterPos, holder.mVoteADButton);
                    }
                }
            }
        });

        return holder;
    }

    private void bindPaginatorViewHolder(RecyclerView.ViewHolder viewHolder) {
        PaginatorViewHolder holder = (PaginatorViewHolder) viewHolder;
        setUpFirstPageButton(holder.mFirstPageButton);
        setUpPreviousPageButton(holder.mPreviousPageButton);
        setUpCurrentPageLabel(holder.mCurrentPageTextView);
    }

    private void bindPostViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final PostViewHolder holder = (PostViewHolder) viewHolder;
        final Post post = mPosts.get(position);

        if (post != null) {
            setUpPersonalityImage(holder.mPersonalityImageView, post);
            setUpPersonalityName(holder.mPersonalityNameTextView, post);
            setUpTimestampSep(holder.mPersonalityTimestampSepTextView, post);
            setUpTimestamp(holder.mTimestampTextView, post);
            setUpPostText(holder.mContentTextView, post);
            setUpADN(holder.mADNLinearLayout, holder.mNumAgreesTextView, holder.mAgreesImageView,
                    holder.mNumDisagreesTextView, holder.mDisagreesImageView,
                    holder.mNumNewsworthiesTextView, holder.mNewsworthiesImageView, post);
            setUpReplyButton(holder.mReplyButton, post);
            setUpNewsworthyButton(holder.mVoteNewsworthyButton, post);
            setUpADButton(holder.mVoteADButton, post);
        }
    }


    // Set Up Paginator
    private void setUpFirstPageButton(ImageButton ib) {
        switch(mCurrentPage) {
            case 1:
                ib.setVisibility(View.GONE);
                break;
            default:
                ib.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setUpPreviousPageButton(ImageButton ib) {
        switch(mCurrentPage) {
            case 1:
                ib.setVisibility(View.GONE);
                break;
            default:
                ib.setVisibility(View.VISIBLE);
                break;
        }
    }
    private void setUpCurrentPageLabel(TextView tv) {
        String pageLabel = String.format("Page %d", mCurrentPage);
        if (!TextUtils.isEmpty(pageLabel)) {
            tv.setText(pageLabel);
        }
    }

    // Set Up Post
    private void setUpPersonalityImage(ImageView iv, Post post) {
        if (post.isAnonymous()) {
            iv.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.profile_picture));
        } else {
            // TODO implement image loader
            iv.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.personality_thumbnail_malloc));
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

        // increment if user has voted on the post
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

    private void setUpReplyButton(Button button, Post post) {
        if (post.getPostTotalReplies() == 0) {
            button.setText("");
        } else {
            String replyCount = String.format("%d replies", post.getPostTotalReplies());
            button.setText(replyCount);
        }
    }

    private void setUpNewsworthyButton(ImageButton button, Post post) {
        if (post.hasVotedNewsworthy() || post.localHasVotedNewsworthy()) {
            // selected disabled
            button.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.ic_post_newsworthy_disabled));
            button.setEnabled(false);
        } else {
            // enabled
            button.setEnabled(true);
        }
    }

    private void setUpADButton(ImageButton button, Post post) {
        if (post.hasVotedAgree() || post.localHasVotedAgree()) {
            // green disabled
            button.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.ic_post_vote_disabled_green));
            button.setEnabled(false);
        } else if (post.hasVotedDisagree() || post.localHasVotedDisagree()) {
            // red disabled
            button.setImageDrawable(ContextCompat.getDrawable(mCtx, R.drawable.ic_post_vote_disabled_red));
            button.setEnabled(false);
        } else {
            // enabled
            button.setEnabled(true);
        }
    }


    // Inner Classes

    public static class PaginatorViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.paginator_action_first)
        ImageButton mFirstPageButton;
        @Bind(R.id.paginator_action_previous)
        ImageButton mPreviousPageButton;
        @Bind(R.id.paginator_current_page)
        RobotoTextView mCurrentPageTextView;
        @Bind(R.id.paginator_action_next)
        ImageButton mNextPageButton;

        public PaginatorViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view)
        CardView mItemView;
        @Bind(R.id.post_personality_image)
        ImageView mPersonalityImageView;
        @Bind(R.id.post_personality_name)
        RobotoTextView mPersonalityNameTextView;
        @Bind(R.id.post_personality_timestamp_sep)
        RobotoTextView mPersonalityTimestampSepTextView;
        @Bind(R.id.post_timestamp)
        RobotoTextView mTimestampTextView;
        @Bind(R.id.post_content)
        RobotoTextView mContentTextView;
        @Bind(R.id.post_adn)
        LinearLayout mADNLinearLayout;
        @Bind(R.id.post_adn_num_agrees)
        RobotoTextView mNumAgreesTextView;
        @Bind(R.id.post_adn_agrees)
        ImageView mAgreesImageView;
        @Bind(R.id.post_adn_num_disagrees)
        RobotoTextView mNumDisagreesTextView;
        @Bind(R.id.post_adn_disagrees)
        ImageView mDisagreesImageView;
        @Bind(R.id.post_adn_num_newsworthies)
        RobotoTextView mNumNewsworthiesTextView;
        @Bind(R.id.post_adn_newsworthies)
        ImageView mNewsworthiesImageView;
        @Bind(R.id.post_action_reply)
        Button mReplyButton;
        @Bind(R.id.post_action_newsworthy)
        ImageButton mVoteNewsworthyButton;
        @Bind(R.id.post_action_vote_ad)
        ImageButton mVoteADButton;

        public PostViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
