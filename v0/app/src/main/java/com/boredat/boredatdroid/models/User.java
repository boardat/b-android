package com.boredat.boredatdroid.models;

import com.boredat.boredatdroid.network.BoredatResponse;

/**
 * Created by Liz on 7/3/2015.
 */
public class User extends BoredatResponse {
    public String networkName;
    public String networkShortname;
    public int userTotalNotifications;
    public String personalityId;
    public String personalityName;

    /*
    public User user;
    public User getUser() {
        return user;
    }

    public static class User {
        private String networkName;
        private String networkShortname;
        private int userTotalNotifications;
        private String personalityId;
        private String personalityName;

        public boolean hasPersonality() {
            return personalityId != null;
        }
        public String getNetworkName() {
            return networkName;
        }

        public String getNetworkShortname() {
            return networkShortname;
        }

        public int getUserTotalNotifications() {
            return userTotalNotifications;
        }

        public String getPersonalityId() {
            return personalityId;
        }

        public String getPersonalityName() {
            return personalityName;
        }
    }
    */
}