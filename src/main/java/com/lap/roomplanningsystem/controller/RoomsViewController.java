package com.lap.roomplanningsystem.controller;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.filterBoxes.FilterBox;
import com.lap.roomplanningsystem.filterBoxes.FilterCheckBox;
import com.lap.roomplanningsystem.filter.Roomfilter;
import com.lap.roomplanningsystem.model.*;
import com.lap.roomplanningsystem.searcher.RoomSearcher;
import com.lap.roomplanningsystem.searcher.Searcher;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoomsViewController extends BaseController{

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private ChoiceBox<String> descriptionChoiceBox;

    @FXML
    private ChoiceBox<String> equipmentChoiceBox;

    @FXML
    private CheckBox imageCheckBox;

    @FXML
    private ChoiceBox<String> locationChoiceBox;

    @FXML
    private ChoiceBox<String> membersChoiceBox;

    @FXML
    private ChoiceBox<String> numberChoiceBox;

    @FXML
    private TableColumn<Room, String> locationColumn;

    @FXML
    private TableColumn<Room, String> numberColumn;

    @FXML
    private TableColumn<Room, Integer> sizeColumn;

    @FXML
    private TableColumn<Room, String> titleColumn;


    private final Roomfilter filter = new Roomfilter();


    @FXML
    private TextField searchField;
    @FXML
    private ImageView profilImage;
    @FXML
    private Button loginButton;


    @FXML
    void initialize() throws SQLException {
        if(model.getUser() != null){
            loginButton.setText("Logout");
            loginButton.setOnAction(this::onLogoutButtonClicked);
            setProfilImage(profilImage);
        }

        initFilterItems();

        roomTable.setItems(model.getDataholder().getRooms());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("R" + String.valueOf(dataFeatures.getValue().getRoomID())));
        titleColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        sizeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Integer>(dataFeatures.getValue().getMaxPersons()));
        locationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLocation().getDescription()));


        roomTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedRoomProperty(nv);
                    showNewView(Constants.PATH_TO_ROOM_DETAIL_VIEW);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedRoomProperty().addListener(new ChangeListener<Room>() {
            @Override
            public void changed(ObservableValue<? extends Room> observableValue, Room oldRoom, Room newRoom) {
                if(newRoom == null){
                    roomTable.getSelectionModel().clearSelection();
                }
            }
        });

    }

    private void initFilterItems() throws SQLException {
        ArrayList<ObservableList<String>> list = Dataholder.roomRepositoryJDBC.listsForChoiceBox(Constants.CALL_LISTS_FOR_CHOICEBOX_ROOM_FILTER);

        filter.addFilterBox(new FilterBox(numberChoiceBox, "Raum-Nr." , list.get(0)));
        filter.addFilterBox(new FilterBox(descriptionChoiceBox, "Raumbeschreibung" , list.get(1)));
        filter.addFilterBox(new FilterBox(membersChoiceBox, "max. Personen" , list.get(2)));
        filter.addFilterBox(new FilterBox(locationChoiceBox, "Standort" , list.get(3)));
        filter.addFilterBox(new FilterBox(equipmentChoiceBox, "Ausstattung" , list.get(4)));
        filter.setImageCheckBox(new FilterCheckBox(imageCheckBox));

        setFilterListenerChoiceBox();
        setFilterListenerCheckbox();

    }

   private void setFilterListenerChoiceBox() {
        filter.getFilterBoxes().addListener(new ListChangeListener<FilterBox>() {
            @Override
            public void onChanged(Change<? extends FilterBox> c) {

                while(c.next()) {
                    FilterBox box = filter.getFilterBoxes().get(c.getFrom());

                    if (c.wasUpdated()) {

                        if(!isBlank(box.getValue())){
                            if(!box.getValue().equals(box.getDefaultValue())){
                                try {
                                    FilteredList<Room> rooms = filter.filterValue(box.getChoiceBox().getId(), box.getValue());
                                    initRoomTable(rooms);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        } else{
                            try {
                                FilteredList <Room> rooms = filter.filterValue(box.getChoiceBox().getId(), box.getValue());
                                initRoomTable(rooms);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            box.getChoiceBox().setValue(box.getDefaultValue());
                        }


                    }
                }

            }
        });

    }

    private void setFilterListenerCheckbox() {
        filter.getImageCheckBox().checkProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {

                try {
                    filter.setImage(newValue);
                    FilteredList<Room> rooms = filter.filter();
                    initRoomTable(rooms);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void initRoomTable(ObservableList<Room> rooms) {
        roomTable.setItems(rooms);
    }

    @FXML
    private void onLoginButtonClicked(ActionEvent actionEvent) {
        model.setPathToView(Constants.PATH_TO_LOGIN_VIEW);
    }

    private void onLogoutButtonClicked(ActionEvent actionEvent){
        model.setAuthorization("standard");
        model.setUser(null);
        loginButton.setText("Logout");
        loginButton.setOnAction(this::onLoginButtonClicked);
    }

    @FXML
    private void onProfilIconClicked(MouseEvent mouseEvent) {
        switch(model.getAuthorization()){
            case "coach", "admin" -> model.setPathToView(Constants.PATH_TO_PROFIL_VIEW);
        }
    }



    @FXML
    private void onSearch(KeyEvent keyEvent) throws Exception {
        String search = searchField.getText();
        FilteredList<Room> rooms = filter.filter();

        if(rooms.size() > 0) {
            if (!search.equals("")) {
                Searcher<Room> searcher = new RoomSearcher();
                ObservableList<Room> searchResults = searcher.search(search, rooms);

                initRoomTable(searchResults);
            } else {

                initRoomTable(rooms);
            }
        }
    }



}
