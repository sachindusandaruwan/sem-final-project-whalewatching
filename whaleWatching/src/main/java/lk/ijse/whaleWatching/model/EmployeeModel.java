package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.*;
import lk.ijse.whaleWatching.dto.tm.EmployeeBoatTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public boolean saveEmployee(EmployeeDto dto) throws SQLException {
        Connection connection= Dbconnection.getInstance().getConnection();
        BoatEmployeeDetailModel boatEmployeeDetailModel = new BoatEmployeeDetailModel();
        connection.setAutoCommit(false);
        try {

            String sql="INSERT INTO employee VALUES(?,?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getEmp_Id());
            pstm.setString(2, dto.getName());
            pstm.setString(3, dto.getAddress());
            pstm.setString(4, dto.getTel());
            pstm.setString(5, dto.getNic());
            pstm.setString(6, dto.getRole());

            boolean isSaved = pstm.executeUpdate()>0;

            if (isSaved) {

                for (EmployeeBoatTm boatTm : dto.getEmployeeBoatTms()) {
                    EmployeeBoatDto boatDto = new EmployeeBoatDto(boatTm.getBoat_Id(), dto.getEmp_Id());
                    boolean isAdded = boatEmployeeDetailModel.save(boatDto, connection);
                    if (!isAdded) {
                        connection.rollback();
                        return false;
                    }
                }

                connection.commit();
                return true;

            } else {
                connection.rollback();
                return false;
            }

        } catch (Exception ex) {
            System.out.println(ex);
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    public EmployeeDto searchEmployeeNIC(String empnic) throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="SELECT * FROM employee WHERE nic=?";
        PreparedStatement pstm= connection.prepareStatement(sql);

        pstm.setString(1,empnic);

        ResultSet resultSet= pstm.executeQuery();
        EmployeeDto dto=null;
        if (resultSet.next()){
            String emp_Id=resultSet.getString(1);
            String emp_Name=resultSet.getString(2);
            String emp_Address=resultSet.getString(3);
            String tel_Num=resultSet.getString(4);
            String Nic=resultSet.getString(5);
            String emp_Role=resultSet.getString(6);

            dto=new EmployeeDto(emp_Id,emp_Name,emp_Address,tel_Num,Nic,emp_Role);
        }
        return dto;
    }

    public boolean deleteEmployee(String empId) throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="DELETE FROM employee WHERE emp_Id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,empId);
        boolean isDeleted=pstm.executeUpdate()>0;
        return isDeleted;

    }

    public List<EmployeeDto> getAllEmployees() throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String empID = resultSet.getString(1);
            String empName = resultSet.getString(2);
            String empAddress=resultSet.getString(3);
            String empTel=resultSet.getString(4);
            String empNic=resultSet.getString(5);
            String empRole=resultSet.getString(6);


            var dto = new EmployeeDto(empID,empName,empAddress,empTel,empNic,empRole);
            dtoList.add(dto);
        }
        return dtoList;
    }
    public List<EmployeeDto> loadAllEmployees() throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()) {
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return empList;
    }

    public boolean updateEmployee(EmployeeDto dto) throws SQLException {

        boolean isUpadate=false;

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="UPDATE employee SET name=?,address=?,tel=?,nic=?,role=? WHERE emp_Id=?";

        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getNic());
        pstm.setString(5, dto.getRole());
        pstm.setString(6, dto.getEmp_Id());

        isUpadate=pstm.executeUpdate()>0;
        return isUpadate;
    }
    public EmployeeDto searchEmployeeId(String id) throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="SELECT * FROM employee WHERE emp_Id=?";
        PreparedStatement pstm= connection.prepareStatement(sql);

        pstm.setString(1,id);

        ResultSet resultSet= pstm.executeQuery();
        EmployeeDto dto=null;
        if (resultSet.next()){
            String emp_Id=resultSet.getString(1);
            String emp_Name=resultSet.getString(2);
            String emp_Address=resultSet.getString(3);
            String tel_Num=resultSet.getString(4);
            String Nic=resultSet.getString(5);
            String emp_Role=resultSet.getString(6);

            dto=new EmployeeDto(emp_Id,emp_Name,emp_Address,tel_Num,Nic,emp_Role);
        }
        return dto;
    }
}
