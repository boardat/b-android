package com.boredat.boredat.util;

/**
 * Created by Liz on 2/5/2016.
 */
public class StringUtils {
    public static String formatPostText(String text) {
        String unescapes[][] = new String[][]{
                {"\\\\", "\\"},
                {"\\\"", "\""},
                {"\\\'", "\'"},
                {"&#32;>&#32;", ">"},
                {"&#9785;", ":("},
                {"&#9786;", ":)"},
                {"&#32;&&#32;", "&"},
                {"&#32;&&#32;hearts;", "<3"}
        };
        for (String[] unesc : unescapes) {
            text = text.replace(unesc[0],unesc[1]);
        }
        return text;
    }
}
