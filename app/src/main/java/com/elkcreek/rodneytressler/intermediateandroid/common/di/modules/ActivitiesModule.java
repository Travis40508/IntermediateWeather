package com.elkcreek.rodneytressler.intermediateandroid.common.di.modules;

import com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView.ChangeLocationFragment;
import com.elkcreek.rodneytressler.intermediateandroid.ui.MainView.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by rodneytressler on 4/18/18.
 */

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract MainActivity contributesMainActivityInjector();

    @ContributesAndroidInjector
    abstract ChangeLocationFragment contributesChangeLocationFragmentInjector();
}
