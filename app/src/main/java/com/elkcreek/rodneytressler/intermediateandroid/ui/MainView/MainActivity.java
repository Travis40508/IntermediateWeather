package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.elkcreek.rodneytressler.intermediateandroid.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity implements MainView {

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

    private LocationListener mLocationListener;
    private LocationManager mLocationManager;

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

                presenter.locationRetrieved(lat, lon);
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
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, mLocationListener);
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
    public void showCurrentTemperature(double currentTemperature) {
        this.currentTemperature.setText(String.valueOf(currentTemperature));
    }

    @Override
    public void showDailySummary(String summary) {
        dailySummary.setText(summary);
    }

    @Override
    public void showIcon(String emoji) {
        weatherIcon.setText(emoji);
    }
}
