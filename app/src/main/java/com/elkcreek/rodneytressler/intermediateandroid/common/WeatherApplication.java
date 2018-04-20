package com.elkcreek.rodneytressler.intermediateandroid.common;

import android.app.Activity;
import android.app.Application;

import com.elkcreek.rodneytressler.intermediateandroid.common.di.components.DaggerApplicationComponent;
import com.elkcreek.rodneytressler.intermediateandroid.common.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.intermediateandroid.common.di.modules.NetworkModule;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class WeatherApplication extends Application implements HasActivityInjector{

    String googleBaseUrl = "https://maps.googleapis.com/maps/api/geocode/";
    String darkSkyBaseUrl = "https://api.darksky.net/forecast/";

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(googleBaseUrl, darkSkyBaseUrl))
                .build()
                .inject(this);
        JodaTimeAndroid.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
