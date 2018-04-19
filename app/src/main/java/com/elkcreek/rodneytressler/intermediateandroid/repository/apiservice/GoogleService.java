package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.GoogleApi;

import io.reactivex.Observable;

/**
 * Created by rodneytressler on 4/18/18.
 */

public interface GoogleService {
    Observable<DarkSkyApi.WeatherResponse> getCurrentLocation(String address);
    Observable<String> getFormattedAddress(String address);
}
