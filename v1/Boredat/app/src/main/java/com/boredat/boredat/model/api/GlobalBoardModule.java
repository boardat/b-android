package com.boredat.boredat.model.api;

import com.boredat.boredat.model.authorization.AccessToken;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Liz on 3/19/2016.
 */
@Module
public class GlobalBoardModule {
    public GlobalBoardModule() {}
    @Provides
    @GlobalBoardScope
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient, AccessToken accessToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(accessToken.getGlobalUrl())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides @GlobalBoardScope
    BoredatService provideBoredatService(Retrofit retrofit) {
        return retrofit.create(BoredatService.class);
    }
}
