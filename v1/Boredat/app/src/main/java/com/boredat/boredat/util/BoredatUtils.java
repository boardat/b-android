package com.boredat.boredat.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Liz on 11/30/2015.
 */
public class BoredatUtils {
    public static String createLocalTitle(String networkShortname) {
        return "Bored at " + networkShortname.substring(0, 1).toUpperCase() + networkShortname.substring(1);
    }

}
