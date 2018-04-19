package com.elkcreek.rodneytressler.intermediateandroid.common.di.modules;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.GoogleApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.DarkSkyService;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.DarkSkyServiceImpl;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.GoogleService;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.GoogleServiceImpl;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodneytressler on 4/18/18.
 */

@Module
public class NetworkModule {

    private String googleBaseUrl;
    private String darkSkyBaseUrl;

    public NetworkModule(String googleBaseUrl, String darkSkyBaseUrl) {
        this.googleBaseUrl = googleBaseUrl;
        this.darkSkyBaseUrl = darkSkyBaseUrl;
    }

    @Provides
    @Named("google retrofit")
    @Singleton
    Retrofit providesGoogleRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(googleBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Named("darksky retrofit")
    @Singleton
    Retrofit providesDarkSkyRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(darkSkyBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    GoogleApi providesGoogleApi(@Named("google retrofit") Retrofit retrofit) {
        GoogleApi googleApi = retrofit.create(GoogleApi.class);

        return googleApi;
    }

    @Provides
    @Singleton
    DarkSkyApi providesDarkSkyApi(@Named("darksky retrofit") Retrofit retrofit) {
        DarkSkyApi darkSkyApi = retrofit.create(DarkSkyApi.class);

        return darkSkyApi;
    }

    @Provides
    @Singleton
    DarkSkyService providesDarkSkyService(DarkSkyApi darkSkyApi) {
        return new DarkSkyServiceImpl(darkSkyApi);
    }

    @Provides
    @Singleton
    GoogleService providesGoogleService(GoogleApi googleApi, DarkSkyService darkSkyService) {
        return new GoogleServiceImpl(googleApi, darkSkyService);
    }


}
