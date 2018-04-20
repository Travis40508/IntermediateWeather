package com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecaseView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.intermediateandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class WeeklyForecastFragment extends Fragment implements WeeklyForecastView {

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static WeeklyForecastFragment newInstance() {

        Bundle args = new Bundle();

        WeeklyForecastFragment fragment = new WeeklyForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
