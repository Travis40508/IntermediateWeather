package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import android.util.Log;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.GoogleApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class GoogleServiceImpl implements GoogleService {

    private final DarkSkyService darkSkyService;
    private GoogleApi googleApi;

    public GoogleServiceImpl(GoogleApi googleApi, DarkSkyService darkSkyService) {
        this.googleApi = googleApi;
        this.darkSkyService = darkSkyService;
    }

    @Override
    public Observable<DarkSkyApi.WeatherResponse> getCurrentLocation(String address) {

        return googleApi.getAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(googleAddress -> !googleAddress.getAddressInformation().isEmpty())
                .map(googleAddress -> googleAddress.getAddressInformation().get(0).getGoogleGeometry().getGoogleLocation())
                .flatMap(googleLocation -> darkSkyService.getWeather(googleLocation.getLatitude(), googleLocation.getLongitude()));
    }

    @Override
    public Observable<String> getFormattedAddress(String address) {
        return googleApi.getAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(googleAddress -> !googleAddress.getAddressInformation().isEmpty())
                .map(googleAddress -> googleAddress.getAddressInformation().get(0).getFormattedAddress());
    }
}
