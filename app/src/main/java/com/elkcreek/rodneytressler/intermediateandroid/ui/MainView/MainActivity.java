package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elkcreek.rodneytressler.intermediateandroid.R;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView.ChangeLocationFragment;
import com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecaseView.WeeklyForecastFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView, ChangeLocationFragment.Callback {

    @Inject protected MainPresenter presenter;

    @BindView(R.id.text_location)
    protected TextView location;

    @BindView(R.id.text_current_temperature)
    protected TextView currentTemperature;

    @BindView(R.id.text_daily_temperature)
    protected TextView dailyTemperature;

    @BindView(R.id.text_summary)
    protected TextView dailySummary;

    @BindView(R.id.image_icon)
    protected TextView weatherIcon;

    @BindView(R.id.frame_layout)
    protected FrameLayout frameLayout;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private ChangeLocationFragment fragment;
    private WeeklyForecastFragment weeklyForecastFragment;
    public static final String WEEKLY_FORECAST_TAG = "weekly_forecast_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter.onCreate(this);



    }



    @Override
    public void getCurrentLocation() {
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();

                String newLocation = lat + ", " + lon;
                presenter.locationRetrieved(newLocation);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 5, mLocationListener);
    }

    @Override
    public void showLocation(String locationText) {
        location.setText(locationText);
    }

    @Override
    public void showDailyTemp(String highTemp, String lowTemp) {
        String dailyTemp = getString(R.string.daily_temperature, highTemp, lowTemp);
        dailyTemperature.setText(dailyTemp);
    }

    @Override
    public void showCurrentTemperature(String currentTemperature) {
        this.currentTemperature.setText(currentTemperature);
    }

    @Override
    public void showDailySummary(String summary) {
        dailySummary.setText(summary);
    }

    @Override
    public void showIcon(String emoji) {
        weatherIcon.setText(emoji);
    }

    @Override
    public void hideFrameLayout() {
        frameLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void areaNotFoundToast() {
        Toast.makeText(this, "Area not found, please try again.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToolbar() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public void showChangeLocationFragment() {
        fragment = ChangeLocationFragment.newInstance();
        fragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();
    }

    @Override
    public void showFrameLayout() {
        frameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWeeklyForecast(List<DarkSkyApi.Days> weeklyForecast) {
        weeklyForecastFragment = WeeklyForecastFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(WEEKLY_FORECAST_TAG, (ArrayList<? extends Parcelable>) weeklyForecast);
        weeklyForecastFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, weeklyForecastFragment).commit();
    }

    @Override
    public void removeChangeLocationFragment() {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public void removeWeeklyForecastFragment() {
        getSupportFragmentManager().beginTransaction().remove(weeklyForecastFragment).commit();
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_change_location :
                presenter.changeLocationClicked();
                return true;
            case R.id.menu_weekly_forecast :
                presenter.weeklyForecastClicked();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void locationChanged(String location) {
        presenter.locationRetrieved(location);
    }

    @Override
    public void detachFragment() {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed(fragment != null && fragment.isAdded(), weeklyForecastFragment != null && weeklyForecastFragment.isAdded());
    }
}
