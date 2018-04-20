package com.elkcreek.rodneytressler.intermediateandroid.repository.apis;



import android.os.Parcel;
import android.os.Parcelable;

import com.elkcreek.rodneytressler.intermediateandroid.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rodneytressler on 4/18/18.
 */

public interface DarkSkyApi {


    @GET(BuildConfig.API_KEY_DARKSKY + "/{lat},{lon}")
    Observable<WeatherResponse> getWeatherResponse(@Path("lat") double lat, @Path("lon") double lon);

    class WeatherResponse {
        @SerializedName("currently")
        @Expose private CurrentWeather currentWeather;

        @SerializedName("daily")
        @Expose private DailyWeather dailyWeather;

        public CurrentWeather getCurrentWeather() {
            return currentWeather;
        }

        public DailyWeather getDailyWeather() {
            return dailyWeather;
        }
    }

    class CurrentWeather {
        @SerializedName("icon")
        @Expose private String icon;

        @SerializedName("precipProbability")
        @Expose private double precipChance;

        @SerializedName("temperature")
        @Expose private double currentTemperature;

        public String getIcon() {
            return icon;
        }

        public double getPrecipChance() {
            return precipChance;
        }

        public double getCurrentTemperature() {
            return currentTemperature;
        }
    }

    class DailyWeather {
        @SerializedName("data")
        @Expose private List<Days> daysList;

        public List<Days> getDaysList() {
            return daysList;
        }
    }

    class Days implements Parcelable {
        @SerializedName("icon")
        @Expose private String icon;

        @SerializedName("summary")
        @Expose private String summary;

        @SerializedName("temperatureHigh")
        @Expose private double highTemp;

        @SerializedName("temperatureLow")
        @Expose private double lowTemp;

        @SerializedName("precipProbability")
        @Expose private double precipChance;

        protected Days(Parcel in) {
            icon = in.readString();
            summary = in.readString();
            highTemp = in.readDouble();
            lowTemp = in.readDouble();
            precipChance = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(icon);
            dest.writeString(summary);
            dest.writeDouble(highTemp);
            dest.writeDouble(lowTemp);
            dest.writeDouble(precipChance);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Days> CREATOR = new Creator<Days>() {
            @Override
            public Days createFromParcel(Parcel in) {
                return new Days(in);
            }

            @Override
            public Days[] newArray(int size) {
                return new Days[size];
            }
        };

        public String getIcon() {
            return icon;
        }

        public String getSummary() {
            return summary;
        }

        public double getHighTemp() {
            return highTemp;
        }

        public double getLowTemp() {
            return lowTemp;
        }

        public double getPrecipChance() {
            return precipChance;
        }

        public String lowTempString() {
            return String.valueOf(lowTemp);
        }

        public String highTempString() {
            return String.valueOf(highTemp);
        }

        public String precipChanceString() {
            return String.valueOf(precipChance);
        }
    }
}
