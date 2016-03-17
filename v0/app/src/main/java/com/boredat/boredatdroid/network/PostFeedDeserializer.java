package com.boredat.boredatdroid.network;

import android.util.Log;

import com.boredat.boredatdroid.models.Post;
import com.boredat.boredatdroid.models.PostFeed;
import com.boredat.boredatdroid.network.BoredatResponse.Error;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liz on 7/22/2015.
 */
public class PostFeedDeserializer implements JsonDeserializer<PostFeed> {
    @Override
    public PostFeed deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Post> postList = new ArrayList<>();
        PostFeed response = new PostFeed();

        if (json.isJsonArray()) {
            Log.d("","PostFeedDeserializer => json.isJsonArray" );
            JsonArray jsonArray = json.getAsJsonArray();

            for (JsonElement obj : jsonArray) {
                Post item;
                if (obj.isJsonObject()) {
                    item = context.deserialize(obj,Post.class);
                    postList.add(item);
                }
            }
            response.setPosts(postList);
        } else if (json.isJsonObject()) {
            Log.d("","PostFeedDeserializer => json.isJsonObject" );
            JsonElement errorField = json.getAsJsonObject().get("error");
            Error error = new Error();
            error = context.deserialize(errorField,Error.class);
            response.setError(error);
        }

        return response;
    }
}
