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
import lk.ijse.whaleWatching.dto.CustomerDto;
import lk.ijse.whaleWatching.dto.tm.CustomerTm;
import lk.ijse.whaleWatching.model.CustomerModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colNIC;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colcus_Id;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtcus_Id;

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colcus_Id.setCellValueFactory(new PropertyValueFactory<>("cus_Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("e_mail"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllCustomers() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.getAllCustomers();

            for(CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getCus_Id(),
                                dto.getName(),
                                dto.getNic(),
                                dto.getAddress(),
                                dto.getE_mail(),
                                dto.getTel()
                        )
                );
            }

            tblCustomer.setItems(obList);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

        clearFields();

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String cus_Id=txtcus_Id.getText();
        String name=txtName.getText();
        String nic=txtNIC.getText();
        String address=txtAddress.getText();
        String email=txtEmail.getText();
        String tel=txtTel.getText();

        var dto = new CustomerDto(cus_Id, name,nic, address,email, tel);

        var model = new CustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                clearFields();
                loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String cus_Id = txtcus_Id.getText();

        var customerModel = new CustomerModel();
        try {
            boolean isDeleted = customerModel.deleteCustomer(cus_Id);

            if(isDeleted) {
                tblCustomer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        boolean isCustomerValidated = ValidateCustomer();

        if (!isCustomerValidated){
            return;
        }

        String cus_Id = txtcus_Id.getText();
        String name = txtName.getText();
        String nic = txtNIC.getText();
        String address = txtAddress.getText();
        String e_mail = txtEmail.getText();
        String tel = txtTel.getText();

        var dto = new CustomerDto(cus_Id,name,nic,address,e_mail,tel);

        var model=new CustomerModel();
        try{
            boolean isSaved=model.saveCustomer(dto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"CUSTOMER IS SAVED!!").show();
                clearFields();
                loadAllCustomers();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"CUSTOMER NOT SAVED").show();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private boolean ValidateCustomer() {
        String idText = txtcus_Id.getText();
        boolean isCustomerIdValidated = Pattern.matches("[C][0-9]{4}", idText);
        if (!isCustomerIdValidated) {
            txtcus_Id.setStyle("-fx-border-color: red");
            new Alert(Alert.AlertType.ERROR, "invalid Customer Id!").show();
            return false;
        }


        String nameText = txtName.getText();
        boolean isCustomerNameValidated = Pattern.matches("[A-Z][a-z](.*)", nameText);
        if(!isCustomerNameValidated){
            txtName.setStyle("-fx-border-color: red");
            new Alert(Alert.AlertType.ERROR, "invalid Customer Name!").show();
            return false;

        }



        String addressText = txtAddress.getText();
        boolean isAddressValidated = Pattern.matches("[A-Z][a-z](.*)",addressText);
        if(!isAddressValidated){
            txtAddress.setStyle("-fx-border-color: red");
            new Alert(Alert.AlertType.ERROR, "invalid Customer Address!").show();
            return false;

        }



        String EmailAddressText = txtEmail.getText();
        boolean isEmailValidated = Pattern.matches("[A-z](.*)(gmail.com)", EmailAddressText);
        if(!isEmailValidated){
            txtEmail.setStyle("-fx-border-color: red");
            new Alert(Alert.AlertType.ERROR, "invalid Customer E-mail Address!").show();
            return false;

        }


        String ContacText = txtTel.getText();
        boolean isContacValidated = Pattern.matches("[0-9]{10}", ContacText);
        if(!isContacValidated){
            txtTel.setStyle("-fx-border-color: red");
            new Alert(Alert.AlertType.ERROR, "invalid Customer Contac No!").show();
            return false;

        }



        return true;


    }

    private void clearFields() {
        txtcus_Id.setText("");
        txtName.setText("");
        txtNIC.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtTel.setText("");

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

        String cus_Id=txtcus_Id.getText();

        var model= new CustomerModel();

        try {
           // System.out.println("enawada");
            CustomerDto dto=model.searchCustomer(cus_Id);

            if(dto!=null){

                fillFields(dto);
            }else {
                    new Alert(Alert.AlertType.INFORMATION,"CUSTOMER NOT FOUND!!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void customerSearchNic(ActionEvent event) {
        String cus_nic=txtNIC.getText();
        var model= new CustomerModel();

        try {
            // System.out.println("enawada");
            CustomerDto dto=model.searchCustomerNIC(cus_nic);

            if(dto!=null){

                fillFields(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"CUSTOMER NOT FOUND!!!").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }


    private void fillFields(CustomerDto dto) {
        txtcus_Id.setText(dto.getCus_Id());
        txtName.setText(dto.getName());
        txtNIC.setText(dto.getNic());
        txtAddress.setText(dto.getAddress());
        txtEmail.setText(dto.getE_mail());
        txtTel.setText(dto.getTel());
    }

}
