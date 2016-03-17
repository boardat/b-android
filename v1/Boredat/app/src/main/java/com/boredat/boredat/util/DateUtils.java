package com.boredat.boredat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Liz on 2/5/2016.
 */
public class DateUtils {
    public static String getFormattedTimestampString(Date date) {

        String pattern = "MMM d, yyyy '@' h:mm a";
        SimpleDateFormat timestamp = new SimpleDateFormat(pattern);

        return timestamp.format(date);
    }
}
