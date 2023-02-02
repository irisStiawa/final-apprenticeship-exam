package com.lap.roomplanningsystem.converter;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.User;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class UserConverter implements ConverterInterface<User>{
    private ObservableList<User> userList;


    public UserConverter() {
        this.userList = BaseController.model.getDataholder().getUsers();

    }


    @Override
    public void setConverter(ComboBox<User> box) {

        box.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return user == null ? "Benutzer" : user.getFirstname() + " " + user.getLastname();
            }

            @Override
            public User fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public User matchByString(String s) {
        for(User u : userList){
            String name = u.getFirstname() + " " + u.getLastname();
            if(s.equals(name)){
                return u;
            }
        }
        return null;
    }

}
