package com.dickensdev.ForecastMyWeather;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ForecastMyWeather extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
       Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
       primaryStage.setTitle("Registration Form FXML Application");
       primaryStage.setScene(new Scene(root, 600, 400));
       primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }


}
