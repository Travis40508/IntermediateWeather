package com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.elkcreek.rodneytressler.intermediateandroid.R;
import com.elkcreek.rodneytressler.intermediateandroid.ui.MainView.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import dagger.android.support.AndroidSupportInjection;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class ChangeLocationFragment extends Fragment implements ChangeLocationView {

    @BindView(R.id.input_change_location)
    protected AutoCompleteTextView changeLocationInput;

    @Inject
    protected ChangeLocationPresenter presenter;
    private Callback callback;
    private ArrayAdapter<String> adapter;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @OnTextChanged(value = R.id.input_change_location, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    protected void textChanged(CharSequence text) {
        presenter.textChanged(text.toString());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_location, container, false);
        ButterKnife.bind(this, view);
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

    @Override
    public void collapseKeyboard() {
        InputMethodManager imm = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void showSuggestions(String[] suggestionsArray) {
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, suggestionsArray);
        changeLocationInput.setAdapter(adapter);
    }

    public interface Callback {
        void locationChanged(String location);
        void detachFragment();
    }
}
