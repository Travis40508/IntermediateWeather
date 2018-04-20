package com.elkcreek.rodneytressler.intermediateandroid.ui.MainView;

import android.location.LocationListener;
import android.util.Log;

import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.DarkSkyService;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apiservice.GoogleService;
import com.elkcreek.rodneytressler.intermediateandroid.utils.Icons;

import org.joda.time.DateTime;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class MainPresenter {

    private final GoogleService googleService;
    private final DarkSkyService darkSkyService;
    private CompositeDisposable compositeDisposable;
    private MainView view;
    private List<DarkSkyApi.Days> weeklyForecast;

    @Inject
    public MainPresenter(GoogleService googleService, DarkSkyService darkSkyService) {
        this.googleService = googleService;
        this.darkSkyService = darkSkyService;
        compositeDisposable = new CompositeDisposable();
        weeklyForecast = new ArrayList<>();
    }

    public void onCreate(MainView view) {
        this.view = view;

        if (view != null) {
            view.getCurrentLocation();
            view.showToolbar();
            view.showFrameLayout();
            view.showProgressBar();
        }
    }

    public void locationRetrieved(String location) {
        weeklyForecast.clear();

        compositeDisposable.add(googleService.getLocation(location)
                .doOnNext(addressInformation -> view.showLocation(addressInformation.getFormattedAddress()))
                .flatMap(addressInformation -> darkSkyService.getWeather(addressInformation.getGoogleGeometry().getGoogleLocation().getLatitude(),
                        addressInformation.getGoogleGeometry().getGoogleLocation().getLongitude()))
                .doOnNext(weatherResponse -> {
                    for(int i = 1; i <= 7; i++) {
                        DarkSkyApi.Days day = weatherResponse.getDailyWeather().getDaysList().get(i);
                        DateTime dateTime = new DateTime();
                        DateTime newDate = dateTime.plusDays(i);
                        day.setDate(newDate.dayOfWeek().getAsShortText() + " " + newDate.monthOfYear().getAsShortText() + ", " + newDate.dayOfMonth().getAsShortText());
                        weeklyForecast.add(day);
                    }
                })
                .subscribe(weatherResponse -> {
                    view.showIcon(showIcon(weatherResponse.getDailyWeather().getDaysList().get(0).getIcon()));
                    view.showDailySummary(weatherResponse.getDailyWeather().getDaysList().get(0).getSummary());
                    view.showDailyTemp(
                            String.valueOf(Math.ceil(weatherResponse.getDailyWeather().getDaysList().get(0).getHighTemp())) + (char) 0x00B0,
                            String.valueOf(Math.ceil(weatherResponse.getDailyWeather().getDaysList().get(0).getLowTemp())) + (char) 0x00B0);
                    view.showCurrentTemperature(String.valueOf(Math.ceil(weatherResponse.getCurrentWeather().getCurrentTemperature())) + (char) 0x00B0);
                    view.hideFrameLayout();
                    view.hideProgressBar();
                }, throwable -> {
                    throwable.printStackTrace();
                    view.areaNotFoundToast();
                }));
    }

    private String showIcon(String icon) {
        int uniCodeIcon = Icons.getWeather(icon);
        String emoji = new String(Character.toChars(uniCodeIcon));
        return emoji;
    }

    public void changeLocationClicked() {
        view.showChangeLocationFragment();
    }

    public void weeklyForecastClicked() {
        view.showWeeklyForecast(weeklyForecast);
    }

    public void backPressed(boolean changeLocationFragmentIsAdded, boolean weeklyForecastFragmentIsAdded) {
        if(changeLocationFragmentIsAdded) {
            view.removeChangeLocationFragment();
        } else if (weeklyForecastFragmentIsAdded) {
            view.removeWeeklyForecastFragment();
        } else {
            view.closeApp();
        }
    }
}
