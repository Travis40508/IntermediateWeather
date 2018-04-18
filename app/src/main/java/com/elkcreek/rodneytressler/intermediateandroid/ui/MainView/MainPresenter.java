package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.util.Log;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.GoogleService;

import javax.inject.Inject;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class MainPresenter {

    private final GoogleService googleService;
    private MainView view;

    @Inject public MainPresenter(GoogleService googleService) {
        this.googleService = googleService;
    }

    public void onCreate(MainView view) {
        this.view = view;

        if(view != null) {
            googleService.getCurrentLocation("Paintsville, Kentucky").subscribe(response -> {
                Log.d("@@@@", String.valueOf(response.getLatitude()));
            }, throwable -> throwable.printStackTrace());
        }
    }
}
