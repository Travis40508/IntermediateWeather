package com.elkcreek.rodneytressler.intermediateandroid.common;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.elkcreek.rodneytressler.intermediateandroid.common.di.components.DaggerApplicationComponent;
import com.elkcreek.rodneytressler.intermediateandroid.common.di.modules.ApplicationModule;
import com.elkcreek.rodneytressler.intermediateandroid.common.di.modules.NetworkModule;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class WeatherApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector{

    String googleBaseUrl = "https://maps.googleapis.com/maps/api/geocode/";
    String autoCompleteBaseUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/";
    String darkSkyBaseUrl = "https://api.darksky.net/forecast/";

    @Inject
    protected DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    @Inject
    protected DispatchingAndroidInjector<Fragment> dispatchingFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .networkModule(new NetworkModule(googleBaseUrl, darkSkyBaseUrl, autoCompleteBaseUrl))
                .build()
                .inject(this);
        JodaTimeAndroid.init(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingFragmentInjector;
    }
}
