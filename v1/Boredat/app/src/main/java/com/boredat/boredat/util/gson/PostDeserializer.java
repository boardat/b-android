package com.boredat.boredat.util.gson;

import com.boredat.boredat.model.api.responses.Post;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Liz on 12/2/2015.
 */
public class PostDeserializer implements JsonDeserializer<Post> {
    @Override
    public Post deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Post post = new Post();
        JsonObject jsonObject = (JsonObject) json;

        post.setPostId(getLongValue(jsonObject.get("postId")));
        post.setPostParentId(getLongValue(jsonObject.get("postParentId")));
        post.setPostText(getStringValue(jsonObject.get("postText")));
        post.setPostCreatedFromString(jsonObject.get("postCreated").getAsString()); // todo make a date or timestamp object
        post.setScreennameName(getStringValue(jsonObject.get("screennameName")));
        post.setNetworkName(getStringValue(jsonObject.get("networkName")));
        post.setNetworkShortname(getStringValue(jsonObject.get("networkShortname")));
        post.setLocationName(getStringValue(jsonObject.get("locationName")));
        post.setPostTotalAgrees(jsonObject.get("postTotalAgrees").getAsInt());
        post.setPostTotalDisagrees(jsonObject.get("postTotalDisagrees").getAsInt());
        post.setPostTotalNewsworthies(jsonObject.get("postTotalNewsworthies").getAsInt());
        post.setPostTotalFavorites(jsonObject.get("postTotalFavorites").getAsInt());
        post.setPostTotalReplies(jsonObject.get("postTotalReplies").getAsInt());
        post.setHasVotedAgree(jsonObject.get("hasVotedAgree").getAsBoolean());
        post.setHasVotedDisagree(jsonObject.get("hasVotedDisagree").getAsBoolean());
        post.setHasVotedNewsworthy(jsonObject.get("hasVotedNewsworthy").getAsBoolean());
        return post;
    }

    private String getStringValue(JsonElement json) {
        if (json == null) {return null;}
        if (json.isJsonNull()) {return null;}
        return json.getAsString();
    }

    private long getLongValue(JsonElement json) {
        if (json == null) {return 0;}

        if (json.isJsonNull()) {
            return 0;
        }
        return json.getAsLong();
    }
}
