package com.boredat.boredat.model.api.responses;

import java.util.List;

/**
 * Created by Liz on 12/2/2015.
 */
public class PostsListResponse extends BoredatResponse {
    public List<Post> posts;

    public PostsListResponse() {

    }
    public List<Post> getPostsList() {return this.posts;}

    public void setPostsList(List<Post> response) {
        this.posts = response;
    }
}
