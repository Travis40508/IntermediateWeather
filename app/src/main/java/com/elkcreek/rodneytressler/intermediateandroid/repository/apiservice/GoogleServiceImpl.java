package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import android.util.Log;

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

    private GoogleApi googleApi;

    public GoogleServiceImpl(GoogleApi googleApi) {
        this.googleApi = googleApi;
    }

    @Override
    public Observable<GoogleApi.GoogleLocation> getCurrentLocation(String address) {

        return googleApi.getAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(googleAddress -> !googleAddress.getAddressInformation().isEmpty())
                .flatMap(googleAddress -> googleAddress.getAddressInformation().get(0).getGoogleGeometry().getGoogleLocation());
    }
}
