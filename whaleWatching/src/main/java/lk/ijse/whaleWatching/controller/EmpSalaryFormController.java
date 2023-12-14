package lk.ijse.whaleWatching.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.EmpSalaryDto;
import lk.ijse.whaleWatching.dto.EmployeeDto;
import lk.ijse.whaleWatching.dto.tm.EmpSalaryTm;
import lk.ijse.whaleWatching.model.EmpSalaryModel;
import lk.ijse.whaleWatching.model.EmployeeModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmpSalaryFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDayPayment;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colSalaryId;
    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblName;

    @FXML
    private Label lblRole;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<EmpSalaryTm> tblEmpSalary;

    @FXML
    private TextField txtPaymentAmount;

    @FXML
    private TextField txtSalaryId;

    @FXML
    private TextField txtSearchId;

    EmployeeModel employeeModel = new EmployeeModel();
    EmpSalaryModel empSalaryModel = new EmpSalaryModel();
    EmployeeModel employeeModels = new EmployeeModel();

    @FXML
    void btnAddAction(ActionEvent event) throws SQLException{
        String SalaryId=txtSalaryId.getText();
        String empId=txtSearchId.getText();
        String date=String.valueOf(LocalDate.now());
        //String empname=lblName.getText();
        double Salary=Double.parseDouble(txtPaymentAmount.getText());
        //String empId=txtSearchId.getText();

        try {
            System.out.println("enawada");
              boolean isUpdate = empSalaryModel.isUpdated(empId,date);
           // System.out.println("enawada");
            if (isUpdate){
                boolean isSaved = empSalaryModel.manage(new EmpSalaryDto(SalaryId,empId, date,Salary));
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, lblName.getText() + "is Salary Mark Successfully").show();
                    initialize();
                }
            }else if (!isUpdate) {
                new Alert(Alert.AlertType.ERROR,lblName.getText() +" Is Salary Is Noted Today").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }

    }
    @FXML
    void btnsalaryDetails(ActionEvent event) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream("/report/SalaryDetails.jrxml");
            JasperDesign load = JRXmlLoader.load(resourceAsStream);

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("select s.date,s.sal_Id,s.emp_Id,e.name,s.amount from salary s join employee e on s.emp_Id=e.emp_Id ");
            load.setQuery(jrDesignQuery);

            JasperReport compiledReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    compiledReport,
                    null,
                    Dbconnection.getInstance().getConnection()
            );
            JasperViewer.viewReport(jasperPrint,false);
        }catch (JRException | SQLException e){
            e.printStackTrace();
        }

    }

    public void initialize(){
        datePicker.setValue(LocalDate.now());
        loadSalary();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        //colSalaryId.setCellValueFactory(new PropertyValueFactory<>("sal_Id"));
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("sal_Id"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("emp_Id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDayPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    private void loadSalary() {
        ObservableList<EmpSalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<EmpSalaryDto> dtoList = empSalaryModel.getAllsalaryDetail();

            for(EmpSalaryDto dto : dtoList) {
                obList.add(
                        new EmpSalaryTm(
                              //  dto.getPay_Id(),
                                //dto.getEmp_Id(),
                             //   dto.getDate(),
                             //   dto.getAmount()
                                dto.getSal_Id(),
                                dto.getEmp_Id(),
                                dto.getDate(),
                                dto.getAmount()
                        )
                );
            }
            tblEmpSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void empFeild(EmployeeDto dto) {
        lblName.setText(dto.getName());
        lblRole.setText(dto.getRole());
    }

    @FXML
    void btnSearchEmpIdAction(ActionEvent event) {
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

}
