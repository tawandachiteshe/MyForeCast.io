package com.dickensdev.ForecastMyWeather.controller;

import com.dickensdev.ForecastMyWeather.model.GeoLocationModel;
import com.dickensdev.ForecastMyWeather.model.GeoResult;
import com.dickensdev.ForecastMyWeather.model.WeatherModel;
import com.dickensdev.ForecastMyWeather.service.GeoLocation;
import com.dickensdev.ForecastMyWeather.service.WeatherService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    private  String geoLocationUrl = "https://api.opencagedata.com";
    private String weatherUrl = "https://api.darksky.net";
    private String apiKey = "fe4215107d29459f853e336409e97048";
    private ClassLoader loader = getClass().getClassLoader();
    @FXML
    private JFXButton submitBtn;
    @FXML
    private JFXTextField placeText;
    @FXML
    private Text timezoneText;

    @FXML
    private Text timeText;

    @FXML
    private Text tempText;

    @FXML
    private Text windText;

    @FXML
    private Text humiText;

    @FXML
    private  Text summaryText;

    @FXML
    private ImageView icon;

    @FXML
    JFXButton refreshBtn;

    public void refreshBtnPressed(){
        getGeoData("harare");
    }
    public void submitBtnPressed(){
        getGeoData(placeText.getText());
    }

    public void placeTextOnKeyPressed(){
        getGeoData(placeText.getText());
    }
    public void initialize(URL location, ResourceBundle resources) {
        getGeoData("harare");
    }

    String secondsToTime(long seconds){

       Date date = new Date(seconds * 1000);
       return date.toGMTString();
    }
    public Retrofit getInfo(String url){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(url).
                addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    public void getGeoData(String place){
        GeoLocation geoLocation = getInfo(geoLocationUrl).create(GeoLocation.class);
        Call<GeoLocationModel> geoLocationData = geoLocation.geoData(place,apiKey);
        geoLocationData.enqueue(new Callback<GeoLocationModel>() {
            public void onResponse(Call<GeoLocationModel> call, Response<GeoLocationModel> response) {
                GeoResult.Bounds bounds = response.body().getResults().get(0).getBounds();
                GeoResult.Bounds.Northeast northeast =  bounds.getNortheast();
                getWeatherData(northeast.getLat(), northeast.getLng());
            }

            public void onFailure(Call<GeoLocationModel> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void getWeatherData(String lat, String lng){
        WeatherService weatherService = getInfo(weatherUrl).create(WeatherService.class);
        Call<WeatherModel> weatherData = weatherService.weatherData(lat,lng);
        weatherData.enqueue(new Callback<WeatherModel>() {
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response){

                WeatherModel weatherModel = response.body();
                if (weatherModel.getCurrentlyModel().getIcon().equals("cloudy")){
                    icon.setImage(new Image(loader.getResource("icons/cloud.png").toString()));
                }else if(weatherModel.getCurrentlyModel().getIcon().equals("clear-day")){
                    icon.setImage(new Image(loader.getResource("icons/sunny.png").toString()));
                }
                else if(weatherModel.getCurrentlyModel().getIcon().equals("rain")){
                    icon.setImage(new Image(loader.getResource("icons/rain.png").toString()));
                }
                else if(weatherModel.getCurrentlyModel().getIcon().equals("partly-cloudy-day")){
                    icon.setImage(new Image(loader.getResource("icons/partly_cloudy_day_.png").toString()));

                }
                else if(weatherModel.getCurrentlyModel().getIcon().equals("partly-cloudy-night")){
                    icon.setImage(new Image(loader.getResource("icons/partly_cloud_night.png").toString()));

                }else {
                    icon.setImage(new Image(loader.getResource("icons/sunny.png").toString()));
                }

                timezoneText.setText(weatherModel.getTimezone());
                String time =  secondsToTime(weatherModel.getCurrentlyModel().getTime());
                timeText.setText(time);
                tempText.setText(""+weatherModel.getCurrentlyModel().getTemperature());
                summaryText.setText(weatherModel.getCurrentlyModel().getSummary());
                humiText.setText(weatherModel.getCurrentlyModel().getHumidity());
                windText.setText(weatherModel.getCurrentlyModel().getWindSpeed());

            }

            public void onFailure(Call<WeatherModel> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }
}
