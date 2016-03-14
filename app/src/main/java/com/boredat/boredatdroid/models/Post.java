package com.boredat.boredatdroid.models;

/**
 * Created by Liz on 7/5/2015.
 */
public class Post {
    private String postId;
    private String postParentId;
    private String postText;
    private String postCreated;
    private String postTotalAgrees;
    private String postTotalDisagrees;
    private String postTotalNewsworthies;
    private String postTotalReplies;
    private Object postType;
    private String networkName;
    private String networkShortname;
    private Object locationName;
    private Object locationShortname;
    private String screennameId;
    private String screennameName;
    private String screennameImage;
    private String localNetworkName;
    private int hasVotedAgree;
    private int hasVotedDisagree;
    private int hasVotedNewsworthy;

    public Post(){}

    public String getPostParentId() {
        return postParentId;
    }

    public void setPostParentId(String postParentId) {
        this.postParentId = postParentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostCreated() {
        return postCreated;
    }

    public void setPostCreated(String postCreated) {
        this.postCreated = postCreated;
    }

    public String getPostTotalAgrees() {
        return postTotalAgrees;
    }

    public void setPostTotalAgrees(String postTotalAgrees) {
        this.postTotalAgrees = postTotalAgrees;
    }

    public String getPostTotalDisagrees() {
        return postTotalDisagrees;
    }

    public void setPostTotalDisagrees(String postTotalDisagrees) {
        this.postTotalDisagrees = postTotalDisagrees;
    }

    public String getPostTotalNewsworthies() {
        return postTotalNewsworthies;
    }

    public void setPostTotalNewsworthies(String postTotalNewsworthies) {
        this.postTotalNewsworthies = postTotalNewsworthies;
    }

    public String getPostTotalReplies() {
        return postTotalReplies;
    }

    public void setPostTotalReplies(String postTotalReplies) {
        this.postTotalReplies = postTotalReplies;
    }

    public Object getPostType() {
        return postType;
    }

    public void setPostType(Object postType) {
        this.postType = postType;
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

    public Object getLocationName() {
        return locationName;
    }

    public void setLocationName(Object locationName) {
        this.locationName = locationName;
    }

    public Object getLocationShortname() {
        return locationShortname;
    }

    public void setLocationShortname(Object locationShortname) {
        this.locationShortname = locationShortname;
    }

    public String getScreennameId() {
        return screennameId;
    }

    public void setScreennameId(String screennameId) {
        this.screennameId = screennameId;
    }

    public String getScreennameName() {
        return screennameName;
    }

    public void setScreennameName(String screennameName) {
        this.screennameName = screennameName;
    }

    public String getScreennameImage() {
        return screennameImage;
    }

    public void setScreennameImage(String screennameImage) {
        this.screennameImage = screennameImage;
    }

    public String getLocalNetworkName() {
        return localNetworkName;
    }

    public void setLocalNetworkName(String localNetworkName) {
        this.localNetworkName = localNetworkName;
    }

    public int getHasVotedAgree() {
        return hasVotedAgree;
    }

    public void setHasVotedAgree(int hasVotedAgree) {
        this.hasVotedAgree = hasVotedAgree;
    }

    public int getHasVotedDisagree() {
        return hasVotedDisagree;
    }

    public void setHasVotedDisagree(int hasVotedDisagree) {
        this.hasVotedDisagree = hasVotedDisagree;
    }

    public int getHasVotedNewsworthy() {
        return hasVotedNewsworthy;
    }

    public void setHasVotedNewsworthy(int hasVotedNewsworthy) {
        this.hasVotedNewsworthy = hasVotedNewsworthy;
    }


    // I miss C :(
    public boolean hasVotedAgree() {return this.hasVotedAgree != 0;}
    public boolean hasVotedDisagree() {return this.hasVotedDisagree != 0;}
    public boolean hasVotedNewsworthy() {return this.hasVotedNewsworthy != 0;}


    public String getFormattedTimestampLocation() {
        String[] dateTime = postCreated.split(" ");
        if (dateTime == null) {
            return postCreated;
        }
        if (dateTime.length !=2) {
            return postCreated;
        }
        String[] date = dateTime[0].split("-");
        String[] time = dateTime[1].split(":");

        if (date == null || time == null) {
            return postCreated;
        }
        if (date.length != 3 || time.length != 3) {
            return postCreated;
        }

        if (locationName != null) {
            return getMonthName(date[1])+date[2]+", "+date[0]+" @ "+getFormattedTimestamp(time) + " from " +locationName;
        }
        return getMonthName(date[1])+date[2]+", "+date[0]+" @ "+getFormattedTimestamp(time);
    }

    /* TODO: handle different timezones!*/
    // returns CST hour as string
    private static String getFormattedTimestamp(String[] s) {
        int gmtOffset = -5;
        boolean pm = false;
        int localHour = Integer.valueOf(s[0]) + gmtOffset;

        if (localHour == 0) {
            localHour = 12;
        } else if (localHour > 12) {
            pm = true;
            localHour -= 12;
        }

        if (pm) {
            return String.valueOf(localHour)+":"+s[1]+"pm";
        }
        return String.valueOf(localHour)+":"+s[1]+"am";
    }

    // returns month name for a given month number
    private static String getMonthName(String s) {
        switch(s) {
            case "01":
                return "Jan ";
            case "02":
                return "Feb ";
            case "03":
                return "Mar ";
            case "04":
                return "Apr ";
            case "05":
                return "May ";
            case "06":
                return "Jun ";
            case "07":
                return "Jul ";
            case "08":
                return "Aug ";
            case "09":
                return "Sep ";
            case "10":
                return "Oct ";
            case "11":
                return "Nov ";
            case "12":
                return "Dec ";
            default:
                return s;
        }
    }

    public String getElapsedTimeLocation() {
        String timeLocation = "";

        return timeLocation;
    }


    public int getPostTotalNumAgrees() {
        return Integer.valueOf(postTotalAgrees);
    }
    public int getPostTotalNumDisagrees() {
        return Integer.valueOf(postTotalDisagrees);
    }
    public int getPostTotalNumNewsworthies() {
        return Integer.valueOf(postTotalNewsworthies);
    }
    public int getPostTotalNumReplies() {
        return Integer.valueOf(postTotalReplies);
    }


}
