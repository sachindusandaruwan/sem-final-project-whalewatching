package lk.ijse.whaleWatching.model;


import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.CustomerDto;
import lk.ijse.whaleWatching.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {


   public boolean saveCustomer(final CustomerDto dto) throws SQLException {

      Connection connection = Dbconnection.getInstance().getConnection();

      String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?)";
      PreparedStatement pstm = connection.prepareStatement(sql);

      pstm.setString(1, dto.getCus_Id());
      pstm.setString(2, dto.getName());
      pstm.setString(3, dto.getNic());
      pstm.setString(4, dto.getAddress());
      pstm.setString(5, dto.getE_mail());
      pstm.setString(6, dto.getTel());

      boolean isSaved = pstm.executeUpdate() > 0;

      return isSaved;

   }

   public CustomerDto searchCustomer(String cus_Id) throws SQLException {

      Connection connection=Dbconnection.getInstance().getConnection();
      String sql="SELECT * FROM customer WHERE cus_Id=?";
      PreparedStatement pstm= connection.prepareStatement(sql);

      pstm.setString(1,cus_Id);

      ResultSet resultSet=pstm.executeQuery();

      CustomerDto dto = null;

      if(resultSet.next()){
         String Id=resultSet.getString(1);
         String cus_name=resultSet.getString(2);
         String cus_nic= resultSet.getString(3);
         String cus_address=resultSet.getString(4);
         String cus_email=resultSet.getString(5);
         String cus_tel=resultSet.getString(6);

         dto = new CustomerDto(Id,cus_name,cus_nic,cus_address,cus_email,cus_tel);

      }
      return dto;
   }

   public boolean updateCustomer(CustomerDto dto) throws SQLException {

     Connection connection = Dbconnection.getInstance().getConnection();

      String sql = "UPDATE customer SET name = ?,nic=?, address = ?,e_mail=?, tel = ? WHERE cus_Id = ?";
      PreparedStatement pstm = connection.prepareStatement(sql);

      pstm.setString(1, dto.getName());
      pstm.setString(2, dto.getNic());
      pstm.setString(3, dto.getAddress());
      pstm.setString(4, dto.getE_mail());
      pstm.setString(5, dto.getTel());
      pstm.setString(6, dto.getCus_Id());

      boolean isUpdate=pstm.executeUpdate() > 0;
      return isUpdate;

   }

   public boolean deleteCustomer(String Id) throws SQLException {
     Connection connection=Dbconnection.getInstance().getConnection();

      String sql = "DELETE FROM customer WHERE cus_Id = ?";
      PreparedStatement pstm = connection.prepareStatement(sql);
      pstm.setString(1,Id);

      boolean isDeleted= pstm.executeUpdate() > 0;
      return isDeleted;
   }

    public List<CustomerDto> getAllCustomers() throws SQLException {
       Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<CustomerDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_nic = resultSet.getString(3);
            String cus_address = resultSet.getString(4);
            String cus_email = resultSet.getString(5);
            String cus_tel = resultSet.getString(6);

            var dto = new CustomerDto(cus_id, cus_name,cus_nic, cus_address,cus_email, cus_tel);
            dtoList.add(dto);
        }
        return dtoList;
    }
   public List<CustomerDto> loadAllCustomers() throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<CustomerDto> cusList = new ArrayList<>();

        while (resultSet.next()) {
            cusList.add(new CustomerDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        return cusList;
    }

    public List<String> findAllIds() throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT cus_Id FROM customer";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> allIds = new ArrayList<>();

        while (resultSet.next()) {
            allIds.add(resultSet.getString(1));
        }
        return allIds;
    }


    public CustomerDto searchCustomerNIC(String cus_nic) throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="SELECT * FROM customer WHERE nic=?";
        PreparedStatement pstm= connection.prepareStatement(sql);

        pstm.setString(1,cus_nic);

        ResultSet resultSet=pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()){
            String Id=resultSet.getString(1);
            String cus_name=resultSet.getString(2);
            String nic= resultSet.getString(3);
            String cus_address=resultSet.getString(4);
            String cus_email=resultSet.getString(5);
            String cus_tel=resultSet.getString(6);

            dto = new CustomerDto(Id,cus_name,nic,cus_address,cus_email,cus_tel);

        }
        return dto;
    }
}


