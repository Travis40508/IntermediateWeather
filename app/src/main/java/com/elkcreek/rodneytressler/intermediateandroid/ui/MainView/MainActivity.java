package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.elkcreek.rodneytressler.intermediateandroid.R;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.ui.ChangeLocationView.ChangeLocationFragment;
import com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecastView.WeeklyForecastFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    @OnClick(R.id.button_current_location)
    protected void onCurrentLocationClicked(View view) {
        presenter.currentLocationClicked();
    }

    private LocationListener mLocationListener;
    private LocationManager mLocationManager;
    private FusedLocationProviderClient providerClient;
    private ChangeLocationFragment fragment;
    private WeeklyForecastFragment weeklyForecastFragment;
    public static final String WEEKLY_FORECAST_TAG = "weekly_forecast_tag";
    private static final int PERMISSION_REQUEST_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermissions();
        } else {
            presenter.onCreate(this);
        }


    }

    private void checkLocationPermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            presenter.onCreate(this);
        }
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_LOCATION : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.onCreate(this);
                } else {
                    Toast.makeText(this, "Please enable locations to use this app!", Toast.LENGTH_SHORT).show();
                    requestLocationPermission();
                }
                return;
            }
        }
    }


    @Override
    public void getCurrentLocation() {
        providerClient = LocationServices.getFusedLocationProviderClient(this);
        providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    String newLocation = location.getLatitude() + ", " + location.getLongitude();
                    presenter.locationRetrieved(newLocation);
                } else {
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
            }
        });
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
        myToolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void showChangeLocationFragment() {
        fragment = ChangeLocationFragment.newInstance();
        fragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.pop_exit).replace(R.id.fragment_holder, fragment).commit();
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
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter, R.anim.pop_exit).replace(R.id.fragment_holder, weeklyForecastFragment).commit();
    }

    @Override
    public void removeChangeLocationFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit).remove(fragment).commit();
    }

    @Override
    public void removeWeeklyForecastFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit).remove(weeklyForecastFragment).commit();
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    @Override
    public void toastEnableLocation() {
        Toast.makeText(this, "Please enable location services and restart this application", Toast.LENGTH_SHORT).show();
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
        mLocationListener = null;
        presenter.locationChanged();
        presenter.locationRetrieved(location);
    }

    @Override
    public void detachFragment() {
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit).remove(fragment).commit();
    }

    @Override
    public void onBackPressed() {
        presenter.backPressed(fragment != null && fragment.isAdded(), weeklyForecastFragment != null && weeklyForecastFragment.isAdded());
    }
}
