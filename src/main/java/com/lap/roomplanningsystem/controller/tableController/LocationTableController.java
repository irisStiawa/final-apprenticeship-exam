package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Location;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LocationTableController extends BaseController {


    @FXML
    private TableColumn<Location, String> locationAdressColumn;

    @FXML
    private TableColumn<Location, String> locationCityColumn;

    @FXML
    private TableColumn<Location, String> locationDescriptionColumn;

    @FXML
    private TableColumn<Location, String> locationNumberColumn;

    @FXML
    private TableColumn<Location, String> locationPostCodeColumn;

    @FXML
    private TableView<Location> locationTableView;

    @FXML
    private TableColumn<Location, String> adressColumn;

    @FXML
    private TableColumn<Location, String> cityColumn;

    @FXML
    private TableColumn<Location, String> descriptionColumn;

    @FXML
    private TableColumn<Location, String> numberColumn;

    @FXML
    private TableColumn<Location, String> postCodeColumn;

    @FXML
    private TableView<Location> tableView;



    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getLocations());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("S" + String.valueOf(dataFeatures.getValue().getLocationID())));
        descriptionColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getDescription()));
        adressColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getAdress()));
        postCodeColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getPostCode()));
        cityColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getCity()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedLocationProperty(nv);
                    showNewView(Constants.PATH_TO_LOCATION_DETAIL_VIEW);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedLocationProperty().addListener(new ChangeListener<Location>() {
            @Override
            public void changed(ObservableValue<? extends Location> observableValue, Location oldLocation, Location newLocation) {
                if(newLocation == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });
    }


}
