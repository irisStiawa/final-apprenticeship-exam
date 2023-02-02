package com.lap.roomplanningsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 1. Einstieg in die Applikation (erwarteter PARAM: Stage)
 * 2. erstellt einen neuen FXMLLoader, welcher das File: main-view.fxml läd
 * 3. erstellt eine neue Scene, welche den FXMLLoader als Parameter erhält
 * 4. definiert den Title der Scene
 * 5. übergibt der Stage die Scene und führt diese aus
 * 6. Los gehts !! :)
 **/

public class RoomplanningsystemApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RoomplanningsystemApplication.class.getResource("views/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Raumplanungssystem");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}