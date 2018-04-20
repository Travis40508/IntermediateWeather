package com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecaseView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elkcreek.rodneytressler.intermediateandroid.R;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.ui.MainView.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class WeeklyForecastFragment extends Fragment implements WeeklyForecastView {

    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    private DailyForecastAdapter adapter;
    private WeeklyForecastPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false);
        ButterKnife.bind(this, view);
        presenter = new WeeklyForecastPresenter();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.attachView(this);
        List<DarkSkyApi.Days> weeklyForecast = getArguments().getParcelableArrayList(MainActivity.WEEKLY_FORECAST_TAG);
        presenter.ForecastRetrieved(weeklyForecast);
    }

    public static WeeklyForecastFragment newInstance() {

        Bundle args = new Bundle();

        WeeklyForecastFragment fragment = new WeeklyForecastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showWeeklyForecast(List<DarkSkyApi.Days> weeklyForecast) {
        adapter = new DailyForecastAdapter(weeklyForecast);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
