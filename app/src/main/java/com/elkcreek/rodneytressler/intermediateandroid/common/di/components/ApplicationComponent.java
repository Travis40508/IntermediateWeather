package com.elkcreek.rodneytressler.intermediateandroid.common.di.components;

import com.elkcreek.rodneytressler.intermediateandroid.common.WeatherApplication;
import com.elkcreek.rodneytressler.intermediateandroid.common.di.modules.ActivitiesModule;
import com.elkcreek.rodneytressler.intermediateandroid.common.di.modules.ApplicationModule;

import dagger.Component;

/**
 * Created by rodneytressler on 4/18/18.
 */

@Component(modules = {ActivitiesModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(WeatherApplication weatherApplication);
}
