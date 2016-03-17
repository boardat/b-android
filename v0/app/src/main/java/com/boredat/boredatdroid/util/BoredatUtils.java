package com.boredat.boredatdroid.util;

import com.boredat.boredatdroid.network.UserSessionManager;

/**
 * Created by Liz on 7/18/2015.
 */
public class BoredatUtils {

    public static String createLocalUrl(String networkShortname) {
        return "https://"+networkShortname+".boredat.com";
    }

    public static String createLocalEndpoint(String networkShortname) {
        return "https://"+networkShortname+".boredat.com/api/v1";
    }

    public static String createAnonUserImageUrl(UserSessionManager session) {
        return  session.getLocalUrl() + "/images/profile_picture.png";
    }

    public static String createAnonUserImageUrl(String networkShortname) {
        return createLocalUrl(networkShortname)+"/images/profile_picture.png";
    }

    public static String createUserPersonalityImageUrl(UserSessionManager session, String imageId) {
        return session.getLocalUrl()+"/images/profile_pictures/"+imageId+"_thumbnail.jpg";
    }
}
