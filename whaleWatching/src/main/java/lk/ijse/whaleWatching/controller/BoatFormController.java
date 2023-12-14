package lk.ijse.whaleWatching.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.whaleWatching.dto.BoatDto;
import lk.ijse.whaleWatching.dto.tm.BoatTm;
import lk.ijse.whaleWatching.dto.tm.CustomerTm;
import lk.ijse.whaleWatching.model.BoatModel;

import java.sql.SQLException;
import java.util.List;

public class BoatFormController {

    @FXML
    private TableColumn<?, ?> colBaott_Id;

    @FXML
    private TableColumn<?, ?> colBoatSheetCount;

    @FXML
    private TableColumn<?, ?> colBoatName;

    @FXML
    private TableColumn<?, ?> colBoatType;

    @FXML
    private TableColumn<?, ?> colDesc;


    @FXML
    private AnchorPane root;

    @FXML
    private TableView<BoatTm> tblBoat;

    @FXML
    private TextField txtBName;

    @FXML
    private TextField txtB_Type;

    @FXML
    private TextField txtBoat_Id;

    @FXML
    private TextField txtDes;

    @FXML
    private TextField txtSheetCount;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String B_Id=txtBoat_Id.getText();

        var model=new BoatModel();
        try{
            boolean isDeleted=model.deleteBoat(B_Id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"BOAT IS DELETED!!!").show();
                clearField();
                loadAllBoat();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"BOAT IS NOT DELETED!!!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        String B_Id=txtBoat_Id.getText();
        String B_Name=txtBName.getText();
        String B_Type=txtB_Type.getText();
        int Sheet_Count=Integer.parseInt(txtSheetCount.getText());
        String Description=txtDes.getText();

        var dto=new BoatDto(B_Id,B_Name,B_Type,Sheet_Count,Description);

        var model=new BoatModel();

        try {

            boolean boatIsSaved = model.saveBoat(dto);
            if (boatIsSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "BOAT IS SAVED").show();
                clearField();
                loadAllBoat();
            } else {
                new Alert(Alert.AlertType.ERROR, "BOAT IS NOT SAVED");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void clearField() {
        txtBoat_Id.setText("");
        txtBName.setText("");
        txtB_Type.setText("");
        txtSheetCount.setText("");
        txtDes.setText("");
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String B_Id=txtBoat_Id.getText();
        String B_Name=txtBName.getText();
        String B_Type=txtB_Type.getText();
        int Sheet_Count=Integer.parseInt(txtSheetCount.getText());
        String desc=txtDes.getText();

        var dto=new BoatDto(B_Id,B_Name,B_Type,Sheet_Count,desc);

        var model=new BoatModel();
        try{

            boolean isUpdate=model.updateBoat(dto);
            if (isUpdate){
                new Alert(Alert.AlertType.CONFIRMATION,"BOAT IS UPDATE!!").show();
                clearField();
                loadAllBoat();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"BOAT IS NOT UPDATE!!!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String boat_Id=txtBoat_Id.getText();

        var model= new BoatModel();

        try {
            BoatDto dto=model.searchBoat(boat_Id);

            if(dto!=null){

                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Boat NOT FOUND!!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void fillFields(BoatDto dto) {
        txtBoat_Id.setText(dto.getB_Id());
        txtBName.setText(dto.getB_Name());
        txtB_Type.setText(dto.getB_Type());
        txtSheetCount.setText(String.valueOf(dto.getSheet_Count()));
        txtDes.setText(dto.getDescription());
    }

    public void initialize() {
        setCellValueFactory();
        loadAllBoat();
    }

    private void setCellValueFactory() {

        colBaott_Id.setCellValueFactory(new PropertyValueFactory<>("boat_Id"));
        colBoatName.setCellValueFactory(new PropertyValueFactory<>("boat_Name"));
        colBoatType.setCellValueFactory(new PropertyValueFactory<>("boat_Type"));
        colBoatSheetCount.setCellValueFactory(new PropertyValueFactory<>("Sheet_Count"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void loadAllBoat() {
        var model = new BoatModel();

        ObservableList<BoatTm> obList = FXCollections.observableArrayList();

        try {
            List<BoatDto> dtoList = model.getAllBoat();

            for(BoatDto dto : dtoList) {
                obList.add(
                        new BoatTm(
                                dto.getB_Id(),
                                dto.getB_Name(),
                                dto.getB_Type(),
                                dto.getSheet_Count(),
                                dto.getDescription()
                        )
                );
            }

            tblBoat.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void searchBoatNameOnAction(ActionEvent event) {
        String bname=txtBName.getText();
        var model= new BoatModel();

        try {
            BoatDto dto=model.searchBoatName(bname);

            if(dto!=null){

                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"BOAT NOT FOUND!!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }



}
