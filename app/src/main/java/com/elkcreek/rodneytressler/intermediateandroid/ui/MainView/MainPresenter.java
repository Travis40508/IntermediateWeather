package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.location.LocationListener;
import android.util.Log;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.DarkSkyService;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.GoogleService;
import com.elkcreek.rodneytressler.intermediateandroid.utils.Icons;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class MainPresenter {

    private final GoogleService googleService;
    private final DarkSkyService darkSkyService;
    private CompositeDisposable compositeDisposable;
    private MainView view;
    private List<DarkSkyApi.Days> weeklyForecast;

    @Inject
    public MainPresenter(GoogleService googleService, DarkSkyService darkSkyService) {
        this.googleService = googleService;
        this.darkSkyService = darkSkyService;
        compositeDisposable = new CompositeDisposable();
        weeklyForecast = new ArrayList<>();
    }

    public void onCreate(MainView view) {
        this.view = view;

        if (view != null) {
            view.getCurrentLocation();
            view.showToolbar();
        }
    }

    public void locationRetrieved(String location) {
        view.showFrameLayout();
        view.showProgressBar();

        compositeDisposable.add(googleService.getLocation(location)
                .doOnNext(addressInformation -> view.showLocation(addressInformation.getFormattedAddress()))
                .flatMap(addressInformation -> darkSkyService.getWeather(addressInformation.getGoogleGeometry().getGoogleLocation().getLatitude(),
                        addressInformation.getGoogleGeometry().getGoogleLocation().getLongitude()))
                .doOnNext(weatherResponse -> {
                    for(int i = 1; i <= 7; i++) {
                        weeklyForecast.add(weatherResponse.getDailyWeather().getDaysList().get(i));
                    }
                })
                .subscribe(weatherResponse -> {
                    showIcon(weatherResponse.getDailyWeather().getDaysList().get(0).getIcon());
                    view.showDailySummary(weatherResponse.getDailyWeather().getDaysList().get(0).getSummary());
                    view.showDailyTemp(
                            String.valueOf(Math.ceil(weatherResponse.getDailyWeather().getDaysList().get(0).getHighTemp())),
                            String.valueOf(Math.ceil(weatherResponse.getDailyWeather().getDaysList().get(0).getLowTemp())));
                    view.showCurrentTemperature(Math.ceil(weatherResponse.getCurrentWeather().getCurrentTemperature()));
                    view.hideFrameLayout();
                    view.hideProgressBar();
                }, throwable -> {
                    view.areaNotFoundToast();
                }));
    }

    private void showIcon(String icon) {
        int uniCodeIcon = Icons.getWeather(icon);
        String emoji = new String(Character.toChars(uniCodeIcon));
        view.showIcon(emoji);
    }

    public void changeLocationClicked() {
        view.showChangeLocationFragment();
    }

}
