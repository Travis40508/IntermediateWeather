package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.location.LocationListener;
import android.util.Log;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.DarkSkyService;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.GoogleService;
import com.elkcreek.rodneytressler.intermediateandroid.utils.Icons;

import javax.inject.Inject;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class MainPresenter {

    private final GoogleService googleService;
    private final DarkSkyService darkSkyService;
    private MainView view;

    @Inject public MainPresenter(GoogleService googleService, DarkSkyService darkSkyService) {
        this.googleService = googleService;
        this.darkSkyService = darkSkyService;
    }

    public void onCreate(MainView view) {
        this.view = view;

        if(view != null) {
            view.getCurrentLocation();
        }
    }

    public void locationRetrieved(double lat, double lon) {
        String location = lat + ", " + lon;

        googleService.getWeather(location)
                .doOnNext(addressInformation -> view.showLocation(addressInformation.getFormattedAddress()))
                .flatMap(addressInformation -> darkSkyService.getWeather(addressInformation.getGoogleGeometry().getGoogleLocation().getLatitude(),
                        addressInformation.getGoogleGeometry().getGoogleLocation().getLongitude()))
                .doOnNext(weatherResponse -> {
                    view.showDailyTemp(
                            String.valueOf(Math.ceil(weatherResponse.getDailyWeather().getDaysList().get(0).getHighTemp())),
                            String.valueOf(Math.ceil(weatherResponse.getDailyWeather().getDaysList().get(0).getLowTemp())));
                })
                .doOnNext(weatherResponse -> view.showDailySummary(weatherResponse.getDailyWeather().getDaysList().get(0).getSummary()))
                .doOnNext(weatherResponse -> showIcon(weatherResponse.getDailyWeather().getDaysList().get(0).getIcon()))
                .subscribe(weatherResponse -> {
                    view.showCurrentTemperature(Math.ceil(weatherResponse.getCurrentWeather().getCurrentTemperature()));
                });
    }

    private void showIcon(String icon) {
        int uniCodeIcon = Icons.getWeather(icon);
        String emoji = new String(Character.toChars(uniCodeIcon));
        view.showIcon(emoji);
    }
}
