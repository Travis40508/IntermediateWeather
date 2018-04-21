package com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.AutoCompleteApi;

import io.reactivex.Observable;

/**
 * Created by rodneytressler on 4/20/18.
 */

public interface AutoCompleteService {
    Observable<AutoCompleteApi.AutoCompleteResponse> getAutoCompleteResponse(String input);
}
