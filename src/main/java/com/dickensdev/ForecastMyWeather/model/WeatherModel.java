package com.dickensdev.ForecastMyWeather.model;

public class WeatherModel {
    private String latitude;
    private String longitude;
    private String timezone;
    private  CurrentlyModel currently;

    public CurrentlyModel getCurrentlyModel() {
        return currently;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimezone() {
        return timezone;
    }

}
