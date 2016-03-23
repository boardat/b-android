package com.boredat.boredat.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.boredat.boredat.R;
import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.util.DateUtils;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Liz on 2/14/2016.
 */
public class RepliesAdapter extends FeedAdapter {
    // Member Variables
    private Context mCtx;
    private OnReplyItemClickListener mOnReplyItemClickListener;

    // Interfaces
    public interface OnReplyItemClickListener {
        void onItemClick(int position, View view);
    }

    // Constructor
    public RepliesAdapter(Context context) {
        super();
        mCtx = context;
    }

    public void setOnItemClickListener(OnReplyItemClickListener onReplyItemClickListener) {
        this.mOnReplyItemClickListener = onReplyItemClickListener;
    }

    // Adapter Methods
    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_condensed_post_item, parent, false);
        final ReplyViewHolder holder = new ReplyViewHolder(view);

        // set click listener
        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = holder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (mOnReplyItemClickListener != null) {
                        mOnReplyItemClickListener.onItemClick(adapterPos, holder.mItemView);
                    }
                }
            }
        });

        return holder;
    }

    @Override
    protected void bind(RecyclerView.ViewHolder viewHolder, int position) {
        final ReplyViewHolder holder = (ReplyViewHolder) viewHolder;
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

    public static class ReplyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.condensed_post_item)
        LinearLayout mItemView;
        @Bind(R.id.condensed_post_personality_image)
        ImageView mPersonalityImageView;
        @Bind(R.id.condensed_post_personality_name)
        RobotoTextView mPersonalityNameTextView;
        @Bind(R.id.condensed_post_personality_timestamp_sep)
        RobotoTextView mPersonalityTimestampSepTextView;
        @Bind(R.id.condensed_post_timestamp)
        RobotoTextView mTimestampTextView;
        @Bind(R.id.condensed_post_content)
        RobotoTextView mContentTextView;
        @Bind(R.id.condensed_post_adn)
        LinearLayout mADNLinearLayout;
        @Bind(R.id.condensed_post_adn_num_agrees)
        RobotoTextView mNumAgreesTextView;
        @Bind(R.id.condensed_post_adn_agrees)
        ImageView mAgreesImageView;
        @Bind(R.id.condensed_post_adn_num_disagrees)
        RobotoTextView mNumDisagreesTextView;
        @Bind(R.id.condensed_post_adn_disagrees)
        ImageView mDisagreesImageView;
        @Bind(R.id.condensed_post_adn_num_newsworthies)
        RobotoTextView mNumNewsworthiesTextView;
        @Bind(R.id.condensed_post_adn_newsworthies)
        ImageView mNewsworthiesImageView;

        public ReplyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
