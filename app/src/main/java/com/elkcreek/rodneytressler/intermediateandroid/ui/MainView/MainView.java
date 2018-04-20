package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;

import java.util.List;

/**
 * Created by rodneytressler on 4/18/18.
 */

public interface MainView {
    void getCurrentLocation();

    void showLocation(String locationText);

    void showDailyTemp(String highTemp, String lowTemp);

    void showCurrentTemperature(double currentTemperature);

    void showDailySummary(String summary);


    void showIcon(String emoji);

    void hideFrameLayout();

    void hideProgressBar();

    void areaNotFoundToast();

    void showToolbar();

    void showChangeLocationFragment();

    void showFrameLayout();

    void showProgressBar();

    void showWeeklyForecast(List<DarkSkyApi.Days> weeklyForecast);

    void removeChangeLocationFragment();

    void removeWeeklyForecastFragment();

    void closeApp();
}
