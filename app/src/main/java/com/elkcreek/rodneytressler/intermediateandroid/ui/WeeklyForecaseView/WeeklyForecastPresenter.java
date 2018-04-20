package com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecaseView;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;

import java.util.List;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class WeeklyForecastPresenter {


    private WeeklyForecastView view;

    public void ForecastRetrieved(List<DarkSkyApi.Days> weeklyForecast) {
        view.showWeeklyForecast(weeklyForecast);
    }

    public void attachView(WeeklyForecastView view) {
        this.view = view;
    }
}
