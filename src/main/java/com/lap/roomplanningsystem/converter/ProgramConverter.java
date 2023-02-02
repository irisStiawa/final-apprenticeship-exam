package com.lap.roomplanningsystem.converter;

import com.lap.roomplanningsystem.controller.BaseController;
import com.lap.roomplanningsystem.model.Program;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class ProgramConverter implements ConverterInterface<Program>{

    private ObservableList<Program> programList;


    public ProgramConverter() {
        this.programList = BaseController.model.getDataholder().getPrograms();

    }



    @Override
    public void setConverter(ComboBox<Program> box) {

        box.setConverter(new StringConverter<Program>() {
            @Override
            public String toString(Program program) {
                return program == null ? "Programm" : program.getDescription();
            }

            @Override
            public Program fromString(String s) {
                return matchByString(s);
            }
        });
    }

    @Override
    public Program matchByString(String s) {
        for(Program p : programList){
            if (p.getDescription().equals(s)){
                return  p;
            }
        }
        return null;
    }


}
