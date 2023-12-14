package lk.ijse.whaleWatching.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.whaleWatching.dto.EmpAttendenceDto;
import lk.ijse.whaleWatching.dto.EmployeeDto;
import lk.ijse.whaleWatching.dto.tm.EmpAttendenceTm;
import lk.ijse.whaleWatching.model.EmpAttendenceModel;
import lk.ijse.whaleWatching.model.EmployeeModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmpAttendenceFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField txtIsAttendence;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colInTime;

    @FXML
    private TableColumn<?, ?> colOutTime;

    @FXML
    private Label lblName;

    @FXML
    private Label lblRole;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmpAttendenceTm> tblEmpAttendence;

    @FXML
    private TextField txtInTime;

    @FXML
    private TextField txtOutTime;

    @FXML
    private TextField txtSearchId;

    @FXML
    private Label lblAName;

    @FXML
    private Label lblArole;


    @FXML
    private Label lblOutTimeA;


    @FXML
    private Label lblInTimeA;

    EmployeeModel employeeModel = new EmployeeModel();
    EmpAttendenceModel empAttendenceModel = new EmpAttendenceModel();
    EmployeeModel employeeModels = new EmployeeModel();
    @FXML
    void btnAddAction(ActionEvent event) throws SQLException {
        String id = txtSearchId.getText();
        String intime = txtInTime.getText();
        String outTime = txtOutTime.getText();
        String date = String.valueOf(LocalDate.now());

        try {
            boolean isUpdate = empAttendenceModel.isUpdated(id,date);
            if (isUpdate){
                boolean isSaved = empAttendenceModel.manage(new EmpAttendenceDto(id, date, intime, outTime));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, lblName.getText() + "is Attendence Mark Successfully").show();
                    initialize();
                }
            }else if (!isUpdate) {
                new Alert(Alert.AlertType.ERROR,lblName.getText() +" Is Attendence Is Noted Today").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }

    }
    @FXML
    void btnSearchEmpIdAction(ActionEvent event) throws SQLException {
        String id = txtSearchId.getText();
        try {
            EmployeeDto dto = employeeModel.searchEmployeeId(id);

            if(dto != null) {
                empFeild(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Employee is not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void empFeild(EmployeeDto dto) {
        lblName.setText(dto.getName());
        lblRole.setText(dto.getRole());
    }


    void loadAttendence() {

        ObservableList<EmpAttendenceTm> obList = FXCollections.observableArrayList();

        try {
            List<EmpAttendenceDto> dtoList = empAttendenceModel.getAllAttendeceDetail();

            for(EmpAttendenceDto dto : dtoList) {
                obList.add(
                        new EmpAttendenceTm(
                                dto.getEmp_Id(),
                                dto.getDate(),
                                dto.getIn_time(),
                                dto.getOut_time()

                        )
                );
            }
            tblEmpAttendence.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colInTime.setCellValueFactory(new PropertyValueFactory<>("in_time"));
        colOutTime.setCellValueFactory(new PropertyValueFactory<>("out_time"));

    }
    public  void  initialize(){
        datePicker.setValue(LocalDate.now());
        loadAttendence();
        setCellValueFactory();
    }

    @FXML
    void btnSearchAttendenceAction(ActionEvent event) throws SQLException {
        String id = txtIsAttendence.getText();
        String date = String.valueOf(datePicker.getValue());

        try {
            EmpAttendenceDto dto = empAttendenceModel.isMarked(id, date);

            if (dto != null) {
                attField(dto);
                new Alert(Alert.AlertType.INFORMATION, lblName.getText() + " Employee is Prsent").show();
            } else {
                new Alert(Alert.AlertType.INFORMATION, lblName.getText() + " Employee is Absent").show();
            }
        }catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void attField(EmpAttendenceDto dto) throws SQLException {
        String id = dto.getEmp_Id();
        EmployeeDto wDto = employeeModels.searchEmployeeId(id);
        lblAName.setText(wDto.getName());
        lblArole.setText(wDto.getRole());
        lblInTimeA.setText(dto.getIn_time());
        lblOutTimeA.setText(dto.getOut_time());

    }
}
