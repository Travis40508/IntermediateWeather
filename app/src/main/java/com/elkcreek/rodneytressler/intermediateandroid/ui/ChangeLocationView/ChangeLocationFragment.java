package com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.elkcreek.rodneytressler.intermediateandroid.R;
import com.elkcreek.rodneytressler.intermediateandroid.ui.MainView.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class ChangeLocationFragment extends Fragment implements ChangeLocationView {

    @BindView(R.id.input_change_location)
    protected EditText changeLocationInput;

    private ChangeLocationPresenter presenter;
    private Callback callback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_location, container, false);
        ButterKnife.bind(this, view);
        presenter = new ChangeLocationPresenter();
        presenter.attachView(this);
        return view;
    }

    @OnEditorAction(R.id.input_change_location)
    protected boolean changeLocationEditorAction(int actionId) {
        switch (actionId) {
            case EditorInfo.IME_ACTION_DONE :
                presenter.donePressed(changeLocationInput.getText().toString());
                return true;
            default :
                return false;
        }
    }

    public static ChangeLocationFragment newInstance() {

        Bundle args = new Bundle();

        ChangeLocationFragment fragment = new ChangeLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void attachParent(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void loadNewLocation(String location) {
        callback.locationChanged(location);
    }

    @Override
    public void detachFragment() {
        callback.detachFragment();
    }

    public interface Callback {
        void locationChanged(String location);
        void detachFragment();
    }
}
