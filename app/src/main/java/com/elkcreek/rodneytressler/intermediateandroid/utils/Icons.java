package com.elkcreek.rodneytressler.intermediateandroid.utils;

/**
 * Created by rodneytressler on 4/18/18.
 */

public class Icons {
    private static final int clear_day = 0x1F31E;
    private static final int clear_night = 0x1F303;
    private static final int rain = 0x2614;
    private static final int snow = 0x2744;
    private static final int sleet = snow;
    private static final int wind = 0x1F343;
    private static final int fog = 0x1F301;
    private static final int cloudy = 0x2601;
    private static final int partly_cloudy_day = 0x26C5;
    private static final int partly_cloudy_night = cloudy;
    private static final int unknown = 0x1F615;
    public static final int degree = 0x00B0;

    public static int getWeather(String weather){
        switch (weather) {
            case "clear-day" : return clear_day;
            case "clear-night" : return clear_night;
            case "rain" : return rain;
            case "snow" : return snow;
            case "sleet" : return sleet;
            case "wind" : return wind;
            case "fog" : return fog;
            case "cloudy" : return cloudy;
            case "partly-cloudy-day" : return partly_cloudy_day;
            case "partly-cloudy-night" : return partly_cloudy_night;
            default: return unknown;
        }
    }
}
