package com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class ChangeLocationPresenter {


    private ChangeLocationView view;

    public void donePressed(String location) {
        view.loadNewLocation(location);
        view.collapseKeyboard();
        view.detachFragment();
    }

    public void attachView(ChangeLocationView view) {
        this.view = view;
    }
}
