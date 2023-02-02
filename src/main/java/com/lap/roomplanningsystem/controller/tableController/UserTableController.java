package com.lap.roomplanningsystem.controller.tableController;

import java.io.IOException;

import com.lap.roomplanningsystem.app.Constants;
import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UserTableController extends BaseController {



    @FXML
    private TableColumn<User, String> authorizationColumn;

    @FXML
    private TableColumn<User, String> firstnameColumn;

    @FXML
    private TableColumn<User, String> iDColumn;

    @FXML
    private TableColumn<User, String> lastnameColumn;

    @FXML
    private TableColumn<User, String> statusColumn;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, String> usernameColumn;

   ;

    @FXML
    void initialize() {
        tableView.setItems(model.getDataholder().getUsers());

        iDColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>("U" + String.valueOf(dataFeatures.getValue().getId())));
        firstnameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getFirstname()));
        lastnameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getLastname()));
        usernameColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().getUsername()));
        authorizationColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().printAuthorization()));
        statusColumn.setCellValueFactory((dataFeatures) -> new SimpleObjectProperty<String>(dataFeatures.getValue().printActiveState()));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            try {
                if(nv != null && !model.isDetailView()){
                    model.setSelectedUserProperty(nv);
                    showNewView(Constants.PATH_TO_USER_DETAIL_VIEW);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        model.selectedUserProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User oldUser, User newUser) {
                if(newUser == null){
                    tableView.getSelectionModel().clearSelection();
                }
            }
        });

    }


}
