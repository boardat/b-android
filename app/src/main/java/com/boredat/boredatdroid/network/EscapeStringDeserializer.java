package com.boredat.boredatdroid.network;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;

/**
 * Created by Liz on 7/22/2015.
 */
public class EscapeStringDeserializer implements JsonDeserializer<String> {


    public static String escapeJSON(String string) {
        String escapes[][] = new String[][]{
                {"\\", "\\\\"},
                {"\"", "\\\""},
                {"\n", "\\n"},
                {"\r", "\\r"},
                {"\b", "\\b"},
                {"\f", "\\f"},
                {"\t", "\\t"}
        };
        for (String[] esc : escapes) {
            string = string.replace(esc[0], esc[1]);
        }
        return string;
    }

    public static String unescapeJSON(String string) {
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
            string = string.replace(unesc[0],unesc[1]);
        }
        return string;
    }

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonPrimitive jsonPrimitive = json.getAsJsonPrimitive();
        String test = jsonPrimitive.getAsString();
//        Log.d("","test: " + test);
//        Log.d("","test.toString()" + test.toString());
        String test2 = unescapeJSON(test);
//        Log.d("","unescapeJSON(test)" + test2);
//        Log.d("", "unescapeJSON(test).toString()");
        return test2;
    }
}