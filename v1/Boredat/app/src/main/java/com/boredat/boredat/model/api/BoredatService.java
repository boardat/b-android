package com.boredat.boredat.model.api;

import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.model.api.responses.PostsListResponse;
import com.boredat.boredat.model.api.responses.ServerMessage;
import com.boredat.boredat.model.api.responses.UserResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Liz on 2/7/2016.
 */
public interface BoredatService {
    // Returns extended information about the currently logged in user
    @GET("user")
    public Observable<UserResponse>
        getUser();

    // Fetch the main feed
    // Returns a list of posts
    @GET("posts")
    public Observable<PostsListResponse>
        getPosts();

    // Fetch the main feed for a specified page
    // Returns a list of posts
    @GET("posts")
    public Observable<PostsListResponse>
        getPostsWithPage(
            @Query("page") int pageNum);

    // Returns a post and its information given a specified id
    @GET("post")
    public Observable<Post>
    getPost(
            @Query("id") String postId);

    // Returns a list of posts (replies) for a given parent id
    @GET("replies")
    public Observable<PostsListResponse>
    getPostReplies(
            @Query("id") String postId);

    // Post a new thought to the main feed
    // anon
    // no location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postToMainFeed(
            @Field(value="text") String text);

    // Post a new thought to the main feed
    // with personality
    // no location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postToMainFeedWithPersonality(
            @Field(value="anonymously") int anonymously,
            @Field(value="text") String text);

    // Post a new thought to the main feed
    // anon
    // with location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postToMainFeedWithLocation(
            @Field(value="locationId") int locationId,
            @Field(value="text") String text);

    // Post a new thought to the main feed
    // with personality
    // with location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postToMainFeedWithPersonalityAndLocation(
            @Field(value="anonymously") int anonymously,
            @Field(value="locationID") int locationId,
            @Field(value="text") String text);

    // Post a reply to an existing post
    // anon
    // no location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postReply(
            @Field(value="id") String postId,
            @Field(value="text") String text);

    // Post a reply to an existing post
    // with personality
    // no location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postReplyWithPersonality(
            @Field(value="id") String postId,
            @Field(value="anonymously") int anonymously,
            @Field(value="text") String text);

    // Post a reply to an existing post
    // anon
    // with location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postReplyWithLocation(
            @Field(value="id") String postId,
            @Field(value="locationId") int locationId,
            @Field(value="text") String text);

    // Post a reply to an existing post
    // with personality
    // with location
    @POST("post")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postReplyWithPersonalityAndLocation(
            @Field(value="id") String postId,
            @Field(value="anonymously") int anonymously,
            @Field(value="locationID") int locationId,
            @Field(value="text") String text);

    // Vote agree for a given id
    @POST("post/agree")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postAgree(
            @Field(value="id") String postId);

    // Vote disagree for a given id
    @POST("post/disagree")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postDisagree(
            @Field(value="id") String postId);

    // Vote newsworthy for a given id
    @POST("post/newsworthy")
    @FormUrlEncoded
    public Observable<ServerMessage>
    postNewsworthy(
            @Field(value="id") String postId);

    // Fetch weeks best feed
    // Returns a list of posts
    @GET("posts/weeks/best")
    public Observable<PostsListResponse>
        getWeeksBest();

    // Fetch weeks best feed for a specified page
    // Returns a list of posts
    @GET("posts/weeks/best")
    public Observable<PostsListResponse>
        getWeeksBestWithPage(
            @Query("page") int pageNum);

    // Fetch weeks worst feed for a specified page
    // Returns a list of posts
    @GET("posts/weeks/worst")
    public Observable<PostsListResponse>
        getWeeksWorst();

    // Fetch weeks worst feed for a specified page
    // Returns a list of posts
    @GET("posts/weeks/worst")
    public Observable<PostsListResponse>
        getWeeksWorstWithPage(
            @Query("page") int pageNum);
}
