package com.boredat.boredat.model.api;

import android.content.SharedPreferences;

import com.boredat.boredat.model.authorization.AccessToken;
import com.boredat.boredat.model.authorization.Account;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by Liz on 2/6/2016.
 */
@Module
public class SessionModule {
    AccessToken mAccessToken;
    Account mAccount;

    // Constructor needs two parameters to instantiate
    public SessionModule(Account account, AccessToken accessToken) {
        this.mAccount = account;
        this.mAccessToken = accessToken;
    }

    @Provides @SessionScope
    AccessToken provideAccessToken() {
        return this.mAccessToken;
    }

    @Provides @SessionScope
    Account provideAccount() {
        return this.mAccount;
    }

    @Provides @SessionScope
    OkHttpClient provideOkHttpClient() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(mAccessToken.getConsumerKey(), mAccessToken.getConsumerSecret());
        consumer.setTokenWithSecret(mAccessToken.getToken(), mAccessToken.getTokenSecret());
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .addInterceptor(logging)
                .build();

        return client;
    }

    @Provides @SessionScope
    UserPreferences provideUserPreferences(SharedPreferences prefs) {
        return new UserPreferences(prefs, mAccount);
    }
}
