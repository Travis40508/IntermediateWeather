package com.elkcreek.rodneytressler.intermediateandroid.common.di.modules;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.AutoCompleteApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.GoogleApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.AutoCompleteService;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.AutoCompleteServiceImpl;
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
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodneytressler on 4/18/18.
 */

@Module
public class NetworkModule {

    private final String autoCompleteBaseUrl;
    private final String googleBaseUrl;
    private final String darkSkyBaseUrl;

    public NetworkModule(String googleBaseUrl, String darkSkyBaseUrl, String autoCompleteBaseUrl) {
        this.googleBaseUrl = googleBaseUrl;
        this.darkSkyBaseUrl = darkSkyBaseUrl;
        this.autoCompleteBaseUrl = autoCompleteBaseUrl;
    }

    @Provides
    @Named("google retrofit")
    @Singleton
    Retrofit providesGoogleRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(googleBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Named("autocomplete retrofit")
    @Singleton
    Retrofit providesAutoCompleteRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(autoCompleteBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    OkHttpClient providesOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    }

    @Provides
    @Named("darksky retrofit")
    @Singleton
    Retrofit providesDarkSkyRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(darkSkyBaseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    AutoCompleteApi providesAutoCompleteApi(@Named("autocomplete retrofit") Retrofit retrofit) {
        AutoCompleteApi autoCompleteApi = retrofit.create(AutoCompleteApi.class);

        return autoCompleteApi;
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

    @Provides
    @Singleton
    AutoCompleteService providesAutoCompleteService(AutoCompleteApi autoCompleteApi) {
        return new AutoCompleteServiceImpl(autoCompleteApi);
    }

}
