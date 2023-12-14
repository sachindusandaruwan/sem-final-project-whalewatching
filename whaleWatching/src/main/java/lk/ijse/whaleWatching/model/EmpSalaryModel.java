package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.EmpSalaryDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpSalaryModel {
    public boolean isUpdated(String empId, String date) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "\n" +
                "SELECT *\n" +
                "FROM salary\n" +
                "WHERE emp_Id = ? AND date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, empId);
        pstm.setDate(2, Date.valueOf(date));

        ResultSet resultSet = pstm.executeQuery();

        int temp = 0;
        boolean isnoted = true;
        while (resultSet.next()){
            temp+=1;
        }
        if (temp>=1){
            isnoted=false;
        }
        return isnoted;
    }

    public boolean manage(EmpSalaryDto salaryDto) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO salary VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, salaryDto.getSal_Id());
        pstm.setString(2, salaryDto.getEmp_Id());
        pstm.setString(3, salaryDto.getDate());
        pstm.setString(4, String.valueOf(salaryDto.getAmount()));

        return pstm.executeUpdate() > 0;
    }

    public List<EmpSalaryDto> getAllsalaryDetail() throws SQLException {

        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM salary";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmpSalaryDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new EmpSalaryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)

                    );

            dtoList.add(dto);
        }

        return dtoList;
    }
}
