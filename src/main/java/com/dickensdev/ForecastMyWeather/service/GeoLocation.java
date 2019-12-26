package com.dickensdev.ForecastMyWeather.service;

import com.dickensdev.ForecastMyWeather.model.GeoLocationModel;
import com.dickensdev.ForecastMyWeather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GeoLocation {
        @GET("/geocode/v1/json")
        Call<GeoLocationModel> geoData(@Query("q") String place, @Query("key") String apiKey);

}
