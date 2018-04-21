package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.AutoCompleteApi;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class AutoCompleteServiceImpl implements AutoCompleteService  {


    private final AutoCompleteApi autoCompleteApi;

    public AutoCompleteServiceImpl(AutoCompleteApi autoCompleteApi) {
        this.autoCompleteApi = autoCompleteApi;
    }

    @Override
    public Observable<AutoCompleteApi.AutoCompleteResponse> getAutoCompleteResponse(String input) {
        return autoCompleteApi.getAutoCompleteResponse(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
