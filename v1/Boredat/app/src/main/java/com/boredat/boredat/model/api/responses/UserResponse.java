package com.boredat.boredat.model.api.responses;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Liz on 11/30/2015.
 */
public class UserResponse extends BoredatResponse {
    @SerializedName("networkName")
    @Expose
    private String networkName;
    @SerializedName("networkShortname")
    @Expose
    private String networkShortname;
    @SerializedName("userLastCheckin")
    @Expose
    private String userLastCheckin;
    @SerializedName("userTotalNotifications")
    @Expose
    private int userTotalNotifications;
    @SerializedName("userTotalNewMessages")
    @Expose
    private String userTotalNewMessages;
    @SerializedName("userJaesPostUnread")
    @Expose
    private int userJaesPostUnread;
    @SerializedName("userProfileDescription")
    @Expose
    private String userProfileDescription;
    @SerializedName("userPoints")
    @Expose
    private String userPoints;
    @SerializedName("userAgrees")
    @Expose
    private int userAgrees;
    @SerializedName("userDisagrees")
    @Expose
    private int userDisagrees;
    @SerializedName("userPostsCount")
    @Expose
    private String userPostsCount;
    @SerializedName("personalityId")
    @Expose
    private String personalityId;
    @SerializedName("personalityName")
    @Expose
    private String personalityName;
    @SerializedName("userTotalRepliesReceived")
    @Expose
    private int userTotalRepliesReceived;
    @SerializedName("userTotalURLClicks")
    @Expose
    private String userTotalURLClicks;

    /**
     *
     * @return
     * The networkName
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     *
     * @param networkName
     * The networkName
     */
    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    /**
     *
     * @return
     * The networkShortname
     */
    public String getNetworkShortname() {
        return networkShortname;
    }

    /**
     *
     * @param networkShortname
     * The networkShortname
     */
    public void setNetworkShortname(String networkShortname) {
        this.networkShortname = networkShortname;
    }

    /**
     *
     * @return
     * The userLastCheckin
     */
    public String getUserLastCheckin() {
        return userLastCheckin;
    }

    /**
     *
     * @param userLastCheckin
     * The userLastCheckin
     */
    public void setUserLastCheckin(String userLastCheckin) {
        this.userLastCheckin = userLastCheckin;
    }

    /**
     *
     * @return
     * The userTotalNotifications
     */
    public int getUserTotalNotifications() {
        return userTotalNotifications;
    }

    /**
     *
     * @param userTotalNotifications
     * The userTotalNotifications
     */
    public void setUserTotalNotifications(int userTotalNotifications) {
        this.userTotalNotifications = userTotalNotifications;
    }

    /**
     *
     * @return
     * The userTotalNewMessages
     */
    public String getUserTotalNewMessages() {
        return userTotalNewMessages;
    }

    /**
     *
     * @param userTotalNewMessages
     * The userTotalNewMessages
     */
    public void setUserTotalNewMessages(String userTotalNewMessages) {
        this.userTotalNewMessages = userTotalNewMessages;
    }

    /**
     *
     * @return
     * The userJaesPostUnread
     */
    public int getUserJaesPostUnread() {
        return userJaesPostUnread;
    }

    /**
     *
     * @param userJaesPostUnread
     * The userJaesPostUnread
     */
    public void setUserJaesPostUnread(int userJaesPostUnread) {
        this.userJaesPostUnread = userJaesPostUnread;
    }

    /**
     *
     * @return
     * The userProfileDescription
     */
    public String getUserProfileDescription() {
        return userProfileDescription;
    }

    /**
     *
     * @param userProfileDescription
     * The userProfileDescription
     */
    public void setUserProfileDescription(String userProfileDescription) {
        this.userProfileDescription = userProfileDescription;
    }

    /**
     *
     * @return
     * The userPoints
     */
    public String getUserPoints() {
        return userPoints;
    }

    /**
     *
     * @param userPoints
     * The userPoints
     */
    public void setUserPoints(String userPoints) {
        this.userPoints = userPoints;
    }

    /**
     *
     * @return
     * The userAgrees
     */
    public int getUserAgrees() {
        return userAgrees;
    }

    /**
     *
     * @param userAgrees
     * The userAgrees
     */
    public void setUserAgrees(int userAgrees) {
        this.userAgrees = userAgrees;
    }

    /**
     *
     * @return
     * The userDisagrees
     */
    public int getUserDisagrees() {
        return userDisagrees;
    }

    /**
     *
     * @param userDisagrees
     * The userDisagrees
     */
    public void setUserDisagrees(int userDisagrees) {
        this.userDisagrees = userDisagrees;
    }

    /**
     *
     * @return
     * The userPostsCount
     */
    public String getUserPostsCount() {
        return userPostsCount;
    }

    /**
     *
     * @param userPostsCount
     * The userPostsCount
     */
    public void setUserPostsCount(String userPostsCount) {
        this.userPostsCount = userPostsCount;
    }

    /**
     *
     * @return
     * The personalityId
     */
    public String getPersonalityId() {
        return personalityId;
    }

    /**
     *
     * @param personalityId
     * The personalityId
     */
    public void setPersonalityId(String personalityId) {
        this.personalityId = personalityId;
    }

    /**
     *
     * @return
     * The personalityName
     */
    public String getPersonalityName() {
        return personalityName;
    }

    /**
     *
     * @param personalityName
     * The personalityName
     */
    public void setPersonalityName(String personalityName) {
        this.personalityName = personalityName;
    }

    /**
     *
     * @return
     * The userTotalRepliesReceived
     */
    public int getUserTotalRepliesReceived() {
        return userTotalRepliesReceived;
    }

    /**
     *
     * @param userTotalRepliesReceived
     * The userTotalRepliesReceived
     */
    public void setUserTotalRepliesReceived(int userTotalRepliesReceived) {
        this.userTotalRepliesReceived = userTotalRepliesReceived;
    }

    /**
     *
     * @return
     * The userTotalURLClicks
     */
    public String getUserTotalURLClicks() {
        return userTotalURLClicks;
    }

    /**
     *
     * @param userTotalURLClicks
     * The userTotalURLClicks
     */
    public void setUserTotalURLClicks(String userTotalURLClicks) {
        this.userTotalURLClicks = userTotalURLClicks;
    }

}
