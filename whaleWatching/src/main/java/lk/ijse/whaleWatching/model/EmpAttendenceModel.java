package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.EmpAttendenceDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class EmpAttendenceModel {

    public boolean isUpdated(String empIds,String date ) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "\n" +
                "SELECT *\n" +
                "FROM attendance\n" +
                "WHERE emp_Id = ? AND date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, empIds);
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
    public boolean manage(EmpAttendenceDto attendenceDTO) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "INSERT INTO attendance VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, attendenceDTO.getEmp_Id());
        pstm.setString(2, attendenceDTO.getDate());
        pstm.setString(3, attendenceDTO.getIn_time());
        pstm.setString(4, attendenceDTO.getOut_time());

        return pstm.executeUpdate() > 0;
    }
    public List<EmpAttendenceDto> getAllAttendeceDetail() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM attendance";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmpAttendenceDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new EmpAttendenceDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)

            );

            dtoList.add(dto);
        }

        return dtoList;
    }
    public EmpAttendenceDto isMarked(String empIds,String date ) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "\n" +
                "SELECT *\n" +
                "FROM attendance\n" +
                "WHERE emp_Id = ? AND date = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, empIds);
        pstm.setDate(2, Date.valueOf(date));

        ResultSet resultSet = pstm.executeQuery();
        EmpAttendenceDto dto = null;

        if (resultSet.next()) {
            String emp_Id = resultSet.getString(1);
            String dates = resultSet.getString(2);
            String in_time = resultSet.getString(3);
            String out_time = resultSet.getString(4);


            dto = new EmpAttendenceDto(emp_Id, dates, in_time, out_time);

        }
        return dto;
    }

}
