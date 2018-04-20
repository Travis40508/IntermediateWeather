package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

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
}
