package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.BoatDto;
import lk.ijse.whaleWatching.dto.EmployeeBoatDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoatEmployeeDetailModel {

    public boolean save(EmployeeBoatDto boatDto, Connection dbConnection) throws SQLException {
        Connection connection = dbConnection;

        String sql="INSERT INTO employee_boat_detail VALUES(?,?)";
        PreparedStatement pstm= connection.prepareStatement(sql);

        pstm.setString(1, boatDto.getBoat_Id());
        pstm.setString(2, boatDto.getEmp_Id());

        return pstm.executeUpdate()>0;
    }

}
