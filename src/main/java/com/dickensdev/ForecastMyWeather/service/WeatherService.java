package com.dickensdev.ForecastMyWeather.service;

import com.dickensdev.ForecastMyWeather.model.WeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface WeatherService {
    public String apiKey = "227949d32ec0fa50a76cfcfb85ba505a";
    @GET("/forecast/" + apiKey + "/{lat},{longy}")
    Call<WeatherModel> weatherData(@Path("lat") String lat, @Path("longy") String longy);
}
