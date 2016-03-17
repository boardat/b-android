package com.boredat.boredatdroid.models;

import com.boredat.boredatdroid.network.BoredatResponse;

import java.util.List;

/**
 * Created by Liz on 7/22/2015.
 */
public class PostFeed extends BoredatResponse{
    public List<Post> posts;

    public List<Post> getFeed() {return this.posts;}

    public void setPosts(List<Post> response) {
        this.posts = response;
    }
}
