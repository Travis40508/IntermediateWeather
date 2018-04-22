package com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView;

import android.util.Log;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.AutoCompleteApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.AutoCompleteService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class ChangeLocationPresenter {


    private final AutoCompleteService autoCompleteService;
    private ChangeLocationView view;

    @Inject
    public ChangeLocationPresenter(AutoCompleteService autoCompleteService) {
        this.autoCompleteService = autoCompleteService;
    }

    public void donePressed(String location) {
        view.loadNewLocation(location);
        view.collapseKeyboard();
        view.detachFragment();
    }

    public void attachView(ChangeLocationView view) {
        this.view = view;
    }

    public void textChanged(String input) {
        List<String> suggestions = new ArrayList<>();
        autoCompleteService.getAutoCompleteResponse(input)
                .map(autoCompleteResponse -> autoCompleteResponse.getPredictionsList())
                .flatMap(predictions -> Observable.fromIterable(predictions))
                .subscribe(prediction -> {
                    suggestions.add(prediction.getDescription());
                    String[] suggestionsArray = suggestions.toArray(new String[suggestions.size()]);
                    view.showSuggestions(suggestionsArray);
                });
    }


}
