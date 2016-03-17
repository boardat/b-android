package com.boredat.boredat.util;

/**
 * Created by Liz on 11/30/2015.
 */
public class BoredatUtils {
    public static String createLocalTitle(String networkShortname) {
        return "Bored at " + networkShortname.substring(0, 1).toUpperCase() + networkShortname.substring(1);
    }
}
