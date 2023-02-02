package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Contract;
import com.lap.roomplanningsystem.model.Course;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ContractTableController extends BaseController {

    @FXML
    private TableColumn<Contract, String> firstnameColumn;

    @FXML
    private TableColumn<Contract, String> lastnameColumn;

    @FXML
    private TableColumn<Contract, String> numberColumn;

    @FXML
    private TableColumn<Contract, Date> startColumn;

    @FXML
    private TableView<Contract> tableView;

    @FXML
    private TableColumn<Contract, String> typColumn;

    @FXML
    void initialize() {

        tableView.setItems(model.getDataholder().getContracts());

        numberColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("V" + dataFeatures.getValue().getId()));
        firstnameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getFirstname()));
        lastnameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLastname()));
        typColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getType().equals("half") ? "Halbjahresvertrag" : "Jahresvertrag"));
        startColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<Date>(dataFeatures.getValue().getStart()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {

            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedContractProperty(nv);
                    switch (nv.getType()){
                        case "year" -> showNewView(Constants.PATH_TO_YEAR_CONTRACT_VIEW);
                        case "half" ->showNewView(Constants.PATH_TO_HALF_YEAR_CONTRACT_VIEW);
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

}
