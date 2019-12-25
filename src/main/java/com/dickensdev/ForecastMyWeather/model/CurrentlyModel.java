package com.dickensdev.ForecastMyWeather.model;

public class CurrentlyModel {
    private long time;
    private String icon;
    private String summary;
    private  String humidity;
    private String windSpeed;
    private String precipType;
    private float temperature;

    public long getTime() {
        return time;
    }

    public String getIcon() {
        return icon;
    }

    public String getSummary() {
        return summary;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getPrecipType() {
        return precipType;
    }

    public float getTemperature() {
        return temperature;
    }
}
