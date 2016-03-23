package com.boredat.boredat.presentation.DetailPost;

import com.boredat.boredat.model.api.responses.Post;

/**
 * Created by Liz on 3/21/2016.
 */
public interface IRepliesFeedPresenter {
    void onLoadReplies(Post post);
    void onLoadDetailReply(int feedId, long postId);
}
