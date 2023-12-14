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
import lk.ijse.whaleWatching.dto.CustomerDto;
import lk.ijse.whaleWatching.dto.EmployeeDto;
import lk.ijse.whaleWatching.dto.tm.EmployeeBoatTm;
import lk.ijse.whaleWatching.dto.tm.EmployeeTm;
import lk.ijse.whaleWatching.model.BoatModel;
import lk.ijse.whaleWatching.model.CustomerModel;
import lk.ijse.whaleWatching.model.EmployeeModel;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {

    //  private final BoatModel boatModel = new BoatModel();

    @FXML
    private JFXComboBox<String> cmbBoatId;

    @FXML
    private TableColumn<?, ?> colEAddress;

    @FXML
    private TableColumn<?, ?> colEAddress1;

    @FXML
    private TableColumn<?, ?> colERole;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEname;

    @FXML
    private TableColumn<?, ?> colEtel;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colemp_Id;

    @FXML
    private TableColumn<?, ?> colemp_Id1;

    @FXML
    private Label lblBoatName;

    @FXML
    private AnchorPane root1;

    @FXML
    private TableView<EmployeeBoatTm> tblBoats;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtemp_Name;

    @FXML
    private TextField txtemp_address;

    @FXML
    private TextField txtemp_nic;

    @FXML
    private TextField txtemp_role;

    @FXML
    private TextField txtemp_tel;

    @FXML
    private Label lblEmployeeId;

    private List<EmployeeBoatTm> employeeBoatTms = new ArrayList<>();

    public void initialize() throws Exception {
        setCellValueFactory();
        loadAllEmployee();
        loadBoatIds();
        loadEmpId();
    }


    private void setCellValueFactory() {

        colemp_Id.setCellValueFactory(new PropertyValueFactory<>("emp_Id"));
        colEname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEtel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colERole.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void loadAllEmployee() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = model.getAllEmployees();

            for (EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getEmp_Id(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getTel(),
                                dto.getNic(),
                                dto.getRole()
                        )
                );
            }

            tblEmployee.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearField();

    }

    private void clearField() {
        txtemp_Name.setText("");
        txtemp_address.setText("");
        txtemp_tel.setText("");
        txtemp_nic.setText("");
        txtemp_role.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String empId = lblEmployeeId.getText();
        var model = new EmployeeModel();
        try {
            boolean isDeleted = model.deleteEmployee(empId);
            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "EMPLOYEE IS DELETED!!").show();
                clearField();
                loadAllEmployee();
            } else {
                new Alert(Alert.AlertType.ERROR, "CUSTOMER IS NOT DELETED!!").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException {

        boolean isEmployeeValidated = ValidateEmployee();

        if (!isEmployeeValidated){
            return;
        }

        String E_Id = lblEmployeeId.getText();
        String E_Name = txtemp_Name.getText();
        String Address = txtemp_address.getText();
        String Tel = txtemp_tel.getText();
        String NIC = txtemp_nic.getText();
        String E_Role = txtemp_role.getText();

        var dto = new EmployeeDto(E_Id, E_Name, Address, Tel, NIC, E_Role, employeeBoatTms);

        var model = new EmployeeModel();
        try {

            boolean isSaved = model.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "EMPLOYEE IS SAVED!!!").show();
                clearField();
                loadBoatIds();
                loadAllEmployee();
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPLOYEE IS NOTSAVED").show();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean ValidateEmployee() {




            String nameText = txtemp_Name.getText();
            boolean isEmployeeNameValidated = Pattern.matches("[A-Z][a-z](.*)", nameText);
            if (!isEmployeeNameValidated) {
                txtemp_Name.setStyle("-fx-border-color: red");
                new Alert(Alert.AlertType.ERROR, "invalid Customer Name!").show();
                return false;

            }


            String addressText = txtemp_address.getText();
            boolean isAddressValidated = Pattern.matches("[A-Z][a-z](.*)", addressText);
            if (!isAddressValidated) {
                txtemp_address.setStyle("-fx-border-color: red");
                new Alert(Alert.AlertType.ERROR, "invalid Customer Address!").show();
                return false;

            }

            String role=txtemp_role.getText();
            boolean isRoleValidated=Pattern.matches("[A-Z][a-z](.*)",role);
            if (!isRoleValidated){
                txtemp_role.setStyle("-fx-border-color: red");
                new Alert(Alert.AlertType.ERROR,"invalid customer Role!").show();
                return false;
            }


            String ContacText = txtemp_tel.getText();
            boolean isContacValidated = Pattern.matches("[0-9]{10}", ContacText);
            if (!isContacValidated) {
                txtemp_tel.setStyle("-fx-border-color: red");
                new Alert(Alert.AlertType.ERROR, "invalid Customer Contac No!").show();
                return false;

            }


            return true;

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        String e_Id = lblEmployeeId.getText();
        String e_Name = txtemp_Name.getText();
        String e_address = txtemp_address.getText();
        String e_Tel = txtemp_tel.getText();
        String e_Nic = txtemp_nic.getText();
        String e_Role = txtemp_role.getText();

        var dto = new EmployeeDto(e_Id, e_Name, e_address, e_Tel, e_Nic, e_Role);

        var model = new EmployeeModel();
        try {

            boolean isUpdate = model.updateEmployee(dto);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "EMPLOYEE IS UPDATE!!").show();
                clearField();
                loadAllEmployee();
            } else {
                new Alert(Alert.AlertType.ERROR, "EMPLOYEE IS NOT UPDATE!!!").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void employeeNicSearchOnAction(ActionEvent event) {

        String emp_nic=txtemp_nic.getText();
        var model= new EmployeeModel();

        try {
            // System.out.println("enawada");
            EmployeeDto dto=model.searchEmployeeNIC(emp_nic);

            if(dto!=null){

                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"CUSTOMER NOT FOUND!!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }




   private void fillFields(EmployeeDto dto) {

        txtemp_Name.setText(dto.getName());
        txtemp_address.setText(dto.getAddress());
        txtemp_tel.setText(dto.getTel());
        txtemp_nic.setText(dto.getTel());
        txtemp_role.setText(dto.getRole());
        lblEmployeeId.setText(dto.getEmp_Id());
        // cmbBoatId.setItems(dto.);
    }

    @FXML
    void btnPlaceOnAction(ActionEvent event) {

    }

    @FXML
    void cmbBoatIdOnAction(ActionEvent event) throws Exception {
        var boatModel = new BoatModel();

        String boatId = cmbBoatId.getSelectionModel().getSelectedItem();
        BoatDto boatDto = boatModel.searchBoat(boatId);

        lblBoatName.setText(boatDto.getB_Name());
    }

    void loadBoatIds() throws Exception{
        var boatModel = new BoatModel();

        List<String> allIds = boatModel.findAllIds();
        cmbBoatId.getItems().clear();
        for (String id: allIds) {
            cmbBoatId.getItems().add(id);
        }

    }

    void loadEmpId() {
        String empId = "EMP001";
        try {
            empId = IDGenerator.getNewID("employee", "emp_Id", "EMP");
            lblEmployeeId.setText(empId);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    @FXML
    void btnAddToTable(ActionEvent event) throws SQLException {
        var boatModel = new BoatModel();
        String selectedItem = cmbBoatId.getSelectionModel().getSelectedItem();

        BoatDto boatDto = boatModel.searchBoat(selectedItem);

        employeeBoatTms.add(new EmployeeBoatTm(boatDto.getB_Id(), boatDto.getB_Name(), boatDto.getB_Type()));

        addToTable();
    }

    void addToTable() {
        tblBoats.setItems(FXCollections.observableArrayList(employeeBoatTms));

        tblBoats.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("boat_Name"));
        tblBoats.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("boat_Type"));
    }


    public void getSelectedRow(javafx.scene.input.MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) //Checking double click
        {
            lblEmployeeId.setText(tblEmployee.getSelectionModel().getSelectedItem().getEmp_Id());
            txtemp_Name.setText(tblEmployee.getSelectionModel().getSelectedItem().getName());
            txtemp_address.setText(tblEmployee.getSelectionModel().getSelectedItem().getAddress());
            txtemp_role.setText(tblEmployee.getSelectionModel().getSelectedItem().getRole());
            txtemp_nic.setText(tblEmployee.getSelectionModel().getSelectedItem().getNic());
            txtemp_tel.setText(tblEmployee.getSelectionModel().getSelectedItem().getTel());
        }
    }
}


