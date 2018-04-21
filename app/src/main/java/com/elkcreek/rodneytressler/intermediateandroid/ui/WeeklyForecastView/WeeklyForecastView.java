package com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecastView;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;

import java.util.List;

/**
 * Created by rodneytressler on 4/20/18.
 */

public interface WeeklyForecastView {
    void showWeeklyForecast(List<DarkSkyApi.Days> weeklyForecast);
}
