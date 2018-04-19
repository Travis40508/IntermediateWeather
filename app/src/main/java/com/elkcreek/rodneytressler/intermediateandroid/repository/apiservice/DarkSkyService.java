package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;

import io.reactivex.Observable;

/**
 * Created by rodneytressler on 4/18/18.
 */

public interface DarkSkyService {
    Observable<DarkSkyApi.WeatherResponse> getWeather(double lat, double lon);
}
