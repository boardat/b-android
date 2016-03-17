package com.boredat.boredat.util.gson;

import android.util.Log;

import com.boredat.boredat.model.api.responses.PostsListResponse;
import com.boredat.boredat.model.api.responses.Post;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.boredat.boredat.model.api.responses.BoredatResponse.Error;

/**
 * Created by Liz on 12/2/2015.
 */
public class PostsListDeserializer implements JsonDeserializer<PostsListResponse> {
    @Override
    public PostsListResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Post> postList = new ArrayList<>();
        PostsListResponse response = new PostsListResponse();

        // if the response is a jsonArray
        if (json.isJsonArray()) {
            Log.d("","PostsListDeserializer => json.isJsonArray" );
            JsonArray jsonArray = json.getAsJsonArray();

            // for each jsonElement in the array
            for (JsonElement obj : jsonArray) {
                Post item;
                if (obj.isJsonObject()) {
                    item = context.deserialize(obj,Post.class);
                    postList.add(item);
                }
            }
            response.setPostsList(postList);
        } else if (json.isJsonObject()) {
            Log.d("", "PostFeedDeserializer => json.isJsonObject");
            JsonElement errorField = json.getAsJsonObject().get("error");
            Error error = new Error();
            error = context.deserialize(errorField,Error.class);
            response.setError(error);
        }

        return response;
    }
}
