package com.lap.roomplanningsystem.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.lap.roomplanningsystem.Calculator.ContractCalculator;
import com.lap.roomplanningsystem.model.Contract;
import com.lap.roomplanningsystem.model.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class YearContractController extends BaseController{


    @FXML
    private Label adressLabel;

    @FXML
    private Label bruttoLabel;

    @FXML
    private Label discountLabel;



    @FXML
    private Label nameLabel;

    @FXML
    private Label nettoDiscountLabel;

    @FXML
    private Label nettoLabel;

    @FXML
    private Label numberLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label ustLabel;

    private Contract contract;

    @FXML
    void initialize() {
        Optional<Contract> optionalContract = model.getDataholder().getContracts().stream().filter(contract -> contract == model.getSelectedContractProperty()).findAny();

        if(optionalContract.isPresent()) {
            contract = optionalContract.get();

            ContractCalculator contractCalculator = new ContractCalculator();
            contractCalculator.calculate(contract.getType());

            titleLabel.setText(contract.getTitle());
            numberLabel.setText("V" + contract.getId());
            nameLabel.setText(contract.getFirstname() + " " + contract.getLastname());
            adressLabel.setText(contract.getAdress() + ", " + contract.getPostCode() + " " + contract.getLand());
            nettoLabel.setText("€ " + contractCalculator.getNetto() + ",00");
            discountLabel.setText("€ " + contractCalculator.getDiscount() + ",00");
            nettoDiscountLabel.setText("€ " + contractCalculator.getNettoDiscount() + ",00");
            ustLabel.setText("€ " + contractCalculator.getTax() + ",00");
            bruttoLabel.setText("€ " + contractCalculator.getBrutto() + ",00");

        }
    }

}
