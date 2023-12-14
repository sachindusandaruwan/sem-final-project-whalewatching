package lk.ijse.whaleWatching.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.whaleWatching.common.IDGenerator;
import lk.ijse.whaleWatching.dto.BoatDto;
import lk.ijse.whaleWatching.dto.RideDto;
import lk.ijse.whaleWatching.dto.tm.RideBoatTm;
import lk.ijse.whaleWatching.dto.tm.RideTm;
import lk.ijse.whaleWatching.model.BoatModel;
import lk.ijse.whaleWatching.model.RideModel;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RideFormController {

    @FXML
    private JFXComboBox<String> cmbBoatId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colRideId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private AnchorPane ridePane;



    @FXML
    private TableColumn<?, ?> colRDate;
    @FXML
    private TableColumn<?, ?> colRTime;


    @FXML
    private TextField txtRTime;

    @FXML
    private DatePicker txtRdate;

    @FXML
    private TableView<RideTm> tblRide;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtRideId;

    @FXML
    private TextField txtType;

    @FXML
    private Label lblBoatType;

    @FXML
    private TableColumn<?, ?> colRStatus;

    @FXML
    private TextField txtRStatus;
    @FXML
    private TableView<RideBoatTm> tblBoats;

    List<RideBoatTm> rideBoatDtos = new ArrayList<>();

    @FXML
    void btnPlaceOnAction(ActionEvent event) throws SQLException {

    }

    public void initialize() throws Exception {
        setCellValueFactory();
        loadAllRide();
        loadBoatIds();

    }

    private void loadAllRide() {
        var model = new RideModel();

        ObservableList<RideTm> obList = FXCollections.observableArrayList();
        try {
            List<RideDto> dtoList = model.getAllRides();

            for (RideDto dto : dtoList) {
                obList.add(
                        new RideTm(
                                dto.getRide_Id(),
                                dto.getPrice(),
                                dto.getType(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getStatus()

                        )
                );
            }

            tblRide.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private void setCellValueFactory() {

        colRideId.setCellValueFactory(new PropertyValueFactory<>("ride_Id"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("one_Person_price"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typ"));
        colRDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colRStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String rideId=txtRideId.getText();
        var model=new RideModel();

        try {
            RideDto dto = model.searchRide(rideId);
            if(dto != null){
                new Alert(Alert.AlertType.CONFIRMATION,"Ride is available!!").show();

                fillField(dto);
            }else {
                new Alert(Alert.AlertType.ERROR,"RIDE NOT FOUND!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void fillField(RideDto dto) {
        txtRideId.setText(dto.getRide_Id());

        txtPrice.setText(String.valueOf(dto.getPrice()));
        txtType.setText(dto.getType());
        txtRdate.setValue(LocalDate.parse(dto.getDate()));
        txtRTime.setText(dto.getTime());
        txtRStatus.setText(dto.getStatus());

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String r_Id=txtRideId.getText();

        var model=new RideModel();
        try {
            boolean isDeleted = model.deleteRide(r_Id);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION,"CUSTOMER IS SAVED!!!").show();
                clearField();
                loadAllRide();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"CUSTOMER NOT DELETED!!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {
        System.out.println("aaaaaaaaaaa");

        RideModel rideModel = new RideModel();

        String rideId =txtRideId.getText();
        double price=Double.parseDouble(txtPrice.getText());
        String type=txtType.getText();
        String date= txtRdate.getValue().toString();
        String time=txtRTime.getText();
        String status=txtRStatus.getText();


        RideDto rideDto = new RideDto(rideId, price, type, date, time,status, rideBoatDtos);

        System.out.println(rideDto);
        boolean isAdded = rideModel.saveRide(rideDto);
        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Ride Added Success").show();
            loadAllRide();
            clearField();
        } else {
            new Alert(Alert.AlertType.ERROR, "Ride Added Failed!");
        }
    }

    private void clearField() {
        txtRideId.setText("");

        txtPrice.setText("");
        txtType.setText("");
        txtRdate.setValue(null);
        txtRTime.setText("");
        txtRStatus.setText("");

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String rideId=txtRideId.getText();

        double Price=Double.parseDouble(txtPrice.getText());
        String type=txtType.getText();
        String date=txtRdate.getValue().toString();
        String time=txtRTime.getText();
        String status=txtRStatus.getText();


        var dto=new RideDto(rideId,Price,type,date,time,status);

        var model=new RideModel();

        try{
            boolean isUpdated=model.updateRide(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"RIDE IS UPDATED").show();
                clearField();
                loadAllRide();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"RIDE IS NOT UPDATED!!!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void cmbBoatOnAction(ActionEvent event) throws SQLException {

        var boatModel = new BoatModel();

        String boatId = cmbBoatId.getSelectionModel().getSelectedItem();
        BoatDto boatDto = boatModel.searchBoat(boatId);

        lblBoatType.setText(boatDto.getB_Type());
    }

    void loadBoatIds() throws Exception{
        var boatModel = new BoatModel();

        List<String> allIds = boatModel.findAllIds();
        cmbBoatId.getItems().clear();
        for (String Bid: allIds) {
            cmbBoatId.getItems().add(Bid);
        }


    }

    @FXML
    void btnAddtoTable(ActionEvent event) throws SQLException {
        BoatModel boatModel = new BoatModel();
        String selectedItem = cmbBoatId.getSelectionModel().getSelectedItem();
        BoatDto boatDto = boatModel.searchBoat(selectedItem);

        RideBoatTm rideBoatTm = new RideBoatTm(boatDto.getB_Id(), boatDto.getB_Name(), boatDto.getB_Type());

        rideBoatDtos.add(rideBoatTm);
        appendTable();
    }

    void appendTable() {
        tblBoats.setItems(FXCollections.observableArrayList(rideBoatDtos));

        tblBoats.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("boat_name"));
        tblBoats.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("boat_type"));
    }

    private String getRideId() {
        String rideId = "R001";
        try {
            rideId = IDGenerator.getNewID("ride", "ride_Id", "R");
        } catch (Exception e) {
            System.out.println(e);
        }
        return rideId;
    }

}
