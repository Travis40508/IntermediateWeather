package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class DarkSkyServiceImpl implements DarkSkyService {

    private DarkSkyApi darkSkyApi;

    public DarkSkyServiceImpl(DarkSkyApi darkSkyApi) {
        this.darkSkyApi = darkSkyApi;
    }

    @Override
    public Observable<DarkSkyApi.WeatherResponse> getWeather(double lat, double lon) {
        return darkSkyApi.getWeatherResponse(lat, lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
