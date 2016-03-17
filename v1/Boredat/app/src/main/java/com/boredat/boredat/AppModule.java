package com.boredat.boredat;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.boredat.boredat.model.api.responses.Post;
import com.boredat.boredat.model.api.responses.PostsListResponse;
import com.boredat.boredat.util.gson.PostDeserializer;
import com.boredat.boredat.util.gson.PostsListDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Liz on 2/6/2016.
 */
@Module
public class AppModule {
    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

    @Provides @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(Post.class, new PostDeserializer())
                .registerTypeAdapter(PostsListResponse.class, new PostsListDeserializer())
                .disableHtmlEscaping();

        return gsonBuilder.create();
    }
}
