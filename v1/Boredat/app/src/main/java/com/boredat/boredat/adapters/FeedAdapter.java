package com.boredat.boredat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.boredat.boredat.model.api.responses.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liz on 2/11/2016.
 */
public abstract class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<Post> mPosts;

    public FeedAdapter() {
        mPosts = new ArrayList<>();
    }

    // Standard adapter methods
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        bind(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public void addAll(List<Post> posts) {
        if (posts != null) {
            for (Post post : posts) {
                addPost(post);
            }
        }
    }

    public void update(Post item) {
        int position = mPosts.indexOf(item);
        if (position >= 0) {
            mPosts.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void remove(Post item) {
        int position = mPosts.indexOf(item);
        if (position >= 0) {
            mPosts.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public Post getItem(int position) {
        if (position >= 0)
            return mPosts.get(position);
        else
            return null;
    }

    // Helper Methods
    public void addPost(Post item) {
        mPosts.add(item);
        notifyItemInserted(getItemCount()-1);
    }

    // These methods must be implemented by extending class
    protected abstract RecyclerView.ViewHolder getViewHolder(ViewGroup parent, int viewType);
    protected abstract void bind(RecyclerView.ViewHolder viewHolder, int position);

}
