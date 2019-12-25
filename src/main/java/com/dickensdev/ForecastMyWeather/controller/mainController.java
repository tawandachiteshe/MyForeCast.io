package com.dickensdev.ForecastMyWeather.controller;

import com.dickensdev.ForecastMyWeather.model.WeatherModel;
import com.dickensdev.ForecastMyWeather.service.WeatherService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class mainController implements Initializable {
    private String weatherUrl = "https://api.darksky.net";
    private ClassLoader loader = getClass().getClassLoader();
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
        getData();
    }

    public void initialize(URL location, ResourceBundle resources) {
        getData();

    }

    String secondsToTime(long seconds){

       Date date = new Date(seconds * 1000);
       return date.toGMTString();
    }

    public void getData(){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(weatherUrl).
                addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherService weatherService = retrofit.create(WeatherService.class);
        Call<WeatherModel> weatherData = weatherService.weatherData("17.8252","31.0335");

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
                    icon.setImage(new Image(loader.getResource("icons/partly-cloudy-day.png").toString()));

                }
                else if(weatherModel.getCurrentlyModel().getIcon().equals("partly-cloudy-night")){
                    icon.setImage(new Image(loader.getResource("icons/partly-cloud-night.png").toString()));

                }else {
                    icon.setImage(new Image(loader.getResource("icons/sunny.png").toString()));
                }

                timezoneText.setText(weatherModel.getTimezone());
                String time =  secondsToTime(weatherModel.getCurrentlyModel().getTime());
                timeText.setText(time);
                tempText.setText(": " + weatherModel.getCurrentlyModel().getTemperature());
                summaryText.setText(weatherModel.getCurrentlyModel().getSummary());
                humiText.setText(": " + weatherModel.getCurrentlyModel().getHumidity());
                windText.setText(": " + weatherModel.getCurrentlyModel().getWindSpeed());
                System.out.println(response.toString());

            }

            public void onFailure(Call<WeatherModel> call, Throwable throwable) {
                System.out.println(throwable.getMessage());
            }
        });
    }
}
