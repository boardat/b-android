package com.boredat.boredat.model.api.responses;

import com.boredat.boredat.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by Liz on 12/2/2015.
 */
public class Post extends BoredatResponse{
    private long postId;
    private long postParentId;
    private String postText;
    private Date postCreated;
    private String screennameName;
    private String networkName;
    private String networkShortname;
    private String locationName;
    private int postTotalAgrees;
    private int postTotalDisagrees;
    private int postTotalNewsworthies;
    private int postTotalFavorites;
    private int postTotalReplies;
    private boolean hasVotedAgree;
    private boolean hasVotedDisagree;
    private boolean hasVotedNewsworthy;

    // used to track local changes
    private boolean localHasVotedAgree = false;

    private boolean localHasVotedDisagree = false;
    private boolean localHasVotedNewsworthy = false;
    private boolean localDidAddReply = false;


    public Post() {

    }

    public boolean isAnonymous() {return this.screennameName == null;}

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getPostParentId() {
        return postParentId;
    }

    public void setPostParentId(long postParentId) {
        this.postParentId = postParentId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = StringUtils.formatPostText(postText);
    }

    public Date getPostCreated() {
        return postCreated;
    }

    public void setPostCreated(Date postCreated) {
        this.postCreated = postCreated;
    }

    public void setPostCreatedFromString(String postCreatedString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.postCreated = formatter.parse(postCreatedString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getScreennameName() {
        return screennameName;
    }

    public void setScreennameName(String screennameName) {
        this.screennameName = screennameName;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkShortname() {
        return networkShortname;
    }

    public void setNetworkShortname(String networkShortname) {
        this.networkShortname = networkShortname;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getPostTotalAgrees() {
        return postTotalAgrees;
    }

    public void setPostTotalAgrees(int postTotalAgrees) {
        this.postTotalAgrees = postTotalAgrees;
    }

    public int getPostTotalDisagrees() {
        return postTotalDisagrees;
    }

    public void setPostTotalDisagrees(int postTotalDisagrees) {
        this.postTotalDisagrees = postTotalDisagrees;
    }

    public int getPostTotalNewsworthies() {
        return postTotalNewsworthies;
    }

    public void setPostTotalNewsworthies(int postTotalNewsworthies) {
        this.postTotalNewsworthies = postTotalNewsworthies;
    }

    public int getPostTotalFavorites() {
        return postTotalFavorites;
    }

    public void setPostTotalFavorites(int postTotalFavorites) {
        this.postTotalFavorites = postTotalFavorites;
    }

    public int getPostTotalReplies() {
        return postTotalReplies;
    }

    public void setPostTotalReplies(int postTotalReplies) {
        this.postTotalReplies = postTotalReplies;
    }

    public boolean hasVotedAgree() {
        return hasVotedAgree;
    }

    public void setHasVotedAgree(boolean hasVotedAgree) {
        this.hasVotedAgree = hasVotedAgree;
    }

    public boolean hasVotedDisagree() {
        return hasVotedDisagree;
    }

    public void setHasVotedDisagree(boolean hasVotedDisagree) {
        this.hasVotedDisagree = hasVotedDisagree;
    }

    public boolean hasVotedNewsworthy() {
        return hasVotedNewsworthy;
    }

    public void setHasVotedNewsworthy(boolean hasVotedNewsworthy) {
        this.hasVotedNewsworthy = hasVotedNewsworthy;
    }

    public boolean localHasVotedDisagree() {
        return localHasVotedDisagree;
    }

    public void setLocalHasVotedDisagree(boolean localHasVotedDisagree) {
        this.localHasVotedDisagree = localHasVotedDisagree;
    }

    public boolean localHasVotedAgree() {
        return localHasVotedAgree;
    }

    public void setLocalHasVotedAgree(boolean localHasVotedAgree) {
        this.localHasVotedAgree = localHasVotedAgree;
    }

    public boolean localHasVotedNewsworthy() {
        return localHasVotedNewsworthy;
    }

    public void setLocalHasVotedNewsworthy(boolean localHasVotedNewsworthy) {
        this.localHasVotedNewsworthy = localHasVotedNewsworthy;
    }

    public boolean localDidAddReply() {
        return localDidAddReply;
    }

    public void setLocalDidAddReply(boolean localDidAddReply) {
        this.localDidAddReply = localDidAddReply;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null) || (o.getClass() != this.getClass()))
            return false;

        // object is a Post
        Post p = (Post) o;
        return postId == p.getPostId()
                && (postCreated == p.getPostCreated() || (null != postCreated && postCreated.equals(p.getPostCreated())));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (int) postId;
        hash = 31 * hash + (null == postCreated ? 0 : postCreated.hashCode());
        return hash;
    }

//    private String locationShortname;
//    private String locationId;
//    private String screennameImage;


//    private String networkId;
//    private String userId;
//    private String accountId;
//    private String screennameId;
//    private String postIPHash;
//    private String postUserAgent;
//    private String postActive;
//    private String postType;

}
