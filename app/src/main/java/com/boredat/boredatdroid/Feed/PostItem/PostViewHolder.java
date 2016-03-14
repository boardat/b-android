package com.boredat.boredatdroid.Feed.PostItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.boredat.boredatdroid.R;
import com.boredat.boredatdroid.models.Post;

/**
 * Created by Liz on 8/3/2015.
 */
public class PostViewHolder extends RecyclerView.ViewHolder implements PostView {
    private final ImageView mPersonalityImage;
    private final TextView mPersonalityName;

    private final TextView mPostText;

    private final TextView mAgreeCount;
    private final TextView mDisagreeCount;
    private final TextView mNewsworthyCount;
    private final ImageView mAgreeIcon;
    private final ImageView mDisagreeIcon;
    private final ImageView mNewsworthyIcon;

    private final ImageButton mOverflowMenu;
    private final ImageButton mReplyButton;
    private final TextView mReplyCount;
    private final TextView mTimestamp;

    private final Context mCtx;
    private Post currentPost;
    private PostPresenter mPresenter;

    public PostViewHolder(View itemView) {
        super(itemView);
        this.mCtx = itemView.getContext();
        this.mPersonalityImage = (ImageView) itemView.findViewById(R.id.post_personality_image);
        this.mPersonalityName = (TextView) itemView.findViewById(R.id.post_personality_name);

        this.mPostText = (TextView) itemView.findViewById(R.id.post_content_text);


        this.mAgreeCount = (TextView) itemView.findViewById(R.id.post_num_agrees);
        this.mDisagreeCount = (TextView) itemView.findViewById(R.id.post_num_disagrees);
        this.mNewsworthyCount = (TextView) itemView.findViewById(R.id.post_num_newsworthies);
        this.mReplyCount = (TextView) itemView.findViewById(R.id.post_num_replies);

        this.mAgreeIcon = (ImageView) itemView.findViewById(R.id.post_agree_icon);
        this.mDisagreeIcon = (ImageView) itemView.findViewById(R.id.post_disagree_icon);
        this.mNewsworthyIcon = (ImageView) itemView.findViewById(R.id.post_newsworthy_icon);

        this.mOverflowMenu = (ImageButton) itemView.findViewById(R.id.post_action_overflow);
        this.mReplyButton = (ImageButton) itemView.findViewById(R.id.post_action_reply);

        this.mTimestamp = (TextView) itemView.findViewById(R.id.post_timestamp_text);
    }

    public void bindPost(Post p) {
        this.currentPost = p;

        // insert personality header if applicable
        if (currentPost.getScreennameName() != null) {
            /* TODO: IMAGE HANDLING*/
            mPersonalityName.setText(currentPost.getScreennameName());
            mPersonalityImage.setVisibility(View.VISIBLE);
            mPersonalityName.setVisibility(View.VISIBLE);
        } else {
            mPersonalityImage.setVisibility(View.GONE);
            mPersonalityName.setVisibility(View.GONE);
        }

        /* insert main post content */
        mPostText.setText(currentPost.getPostText());


        // num agrees
        if (currentPost.getPostTotalNumAgrees() > 0) {
            mAgreeCount.setText(currentPost.getPostTotalAgrees() + " agree");
            mAgreeCount.setVisibility(View.VISIBLE);
            mAgreeIcon.setVisibility(View.VISIBLE);
        } else {
            mAgreeCount.setVisibility(View.GONE);
            mAgreeIcon.setVisibility(View.GONE);
        }

        // num disagrees
        if (currentPost.getPostTotalNumDisagrees() > 0) {
            mDisagreeCount.setText(currentPost.getPostTotalDisagrees() + " disagree");
            mDisagreeCount.setVisibility(View.VISIBLE);
            mDisagreeIcon.setVisibility(View.VISIBLE);
        } else {
            mDisagreeCount.setVisibility(View.GONE);
            mDisagreeIcon.setVisibility(View.GONE);
        }

        // num newsworthies
        if (currentPost.getPostTotalNumNewsworthies() > 0) {
            mNewsworthyCount.setText(currentPost.getPostTotalNewsworthies() + " ");
            mNewsworthyCount.setVisibility(View.VISIBLE);
            mNewsworthyIcon.setVisibility(View.VISIBLE);
        } else {
            mNewsworthyCount.setVisibility(View.GONE);
            mNewsworthyIcon.setVisibility(View.GONE);
        }

        // num replies
        if (currentPost.getPostTotalNumReplies() > 0) {
            mReplyCount.setText(currentPost.getPostTotalReplies());
            mReplyCount.setVisibility(View.VISIBLE);
        } else {
            mReplyCount.setVisibility(View.GONE);
            mReplyCount.setVisibility(View.GONE);
        }

        // insert timestamp
        mTimestamp.setText(currentPost.getFormattedTimestampLocation());

        // click listeners
        mOverflowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mCtx,mOverflowMenu);
                popup.getMenuInflater().inflate(R.menu.popup_menu,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(mCtx, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()) {
                            case R.id.pop_action_agree:
                                Log.d("", "pop_action_agree ( " + currentPost.getPostId() + ")");
                                getmPresenter().onVoteAgree(currentPost.getPostId());
                                break;
                            case R.id.pop_action_disagree:
                                Log.d("", "pop_action_disagree ( " + currentPost.getPostId() + ")");
                                getmPresenter().onVoteDisagree(currentPost.getPostId());
                                break;
                            case R.id.pop_action_newsworthy:
                                Log.d("", "pop_action_newsworthy ( " + currentPost.getPostId() + ")");
                                getmPresenter().onVoteNewsworthy(currentPost.getPostId());
                                break;
                            default:
                                Log.d("", "=> default in switch");
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    private PostPresenter getmPresenter() {
        if (mPresenter == null) {
            mPresenter = new PostPresenterImpl(mCtx,this);
        }
        return mPresenter;
    }

    @Override
    public void showAgree() {
        showMessage("showAgree");
    }

    @Override
    public void showDisagree() {
        showMessage("showDisagree");
    }

    @Override
    public void showNewsworthy() {
        showMessage("showNewsworthy");

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
    }
}
