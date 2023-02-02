package com.lap.roomplanningsystem.controller;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.model.Event;
import com.lap.roomplanningsystem.model.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



/**
 * Die Klasse StatisticViewController ist eine Kindklasse des BaseController.
 * Kontrollerfunktionen: statistic-view.fxml
 **/
public class StatisticViewController extends BaseController{

    @FXML
    private ImageView profilImage;

    @FXML
    private BarChart<String, Number> statisticArea;

    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Integer> count = new ArrayList<>();



    @FXML
    void initialize() {
        assert profilImage != null : "fx:id=\"profilImage\" was not injected: check your FXML file 'statistic-view.fxml'.";
        assert statisticArea != null : "fx:id=\"statisticArea\" was not injected: check your FXML file 'statistic-view.fxml'.";

        if(model.getUser()!= null){
            setProfilImage(profilImage);
        }


        createStatistic();

    }


    private void createStatistic() {
        ObservableList<Event> events = model.getDataholder().getEvents();
        ObservableList<Room> bookedRooms = FXCollections.observableArrayList();

        events.forEach(e -> bookedRooms.add(e.getRoom()));

        statisticArea.getXAxis().setLabel("Räume");
        statisticArea.getYAxis().setLabel("Anzahl der Buchungen");

        findFrequent(bookedRooms);

        if(rooms.size() > 0) {
            XYChart.Series series = new XYChart.Series();
            series.setName("beliebteste Räume");
            series.getData().add(new XYChart.Data(rooms.get(0).getDescription() + " " + rooms.get(0).getLocation().getDescription(), count.get(0)));
            series.getData().add(new XYChart.Data(rooms.get(1).getDescription() + " " + rooms.get(1).getLocation().getDescription(), count.get(1)));
            series.getData().add(new XYChart.Data(rooms.get(2).getDescription() + " " + rooms.get(2).getLocation().getDescription(), count.get(2)));

            statisticArea.getData().add(series);
        }
    }

    private void findFrequent(ObservableList<Room> bookedRooms) {

        for(int repeat = 1; repeat <= 3; repeat++){
            Room room = null;
            int maxFreq = 0;
            for (int i = 0; i < bookedRooms.size(); i++) {
                int count = 0;

                for (int j = 0; j < bookedRooms.size(); j++) {
                    if (bookedRooms.get(i).getRoomID() == bookedRooms.get(j).getRoomID()) {
                        count++;
                    }
                }

                if (count > maxFreq) {
                    maxFreq = count;
                    room = bookedRooms.get(i);
                }
            }

            rooms.add(room);
            count.add(maxFreq);
            Room finalRoom = room;
            bookedRooms.removeIf(r -> r.getRoomID() == finalRoom.getRoomID());
        }

    }


    @FXML
    void onLogoutButtonClicked(ActionEvent event) {
        model.setAuthorization("standard");
        model.setUser(null);
        model.setPathToView(Constants.PATH_TO_HOME_VIEW);

    }

    @FXML
    void onProfilIconClicked(MouseEvent event) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }

}
