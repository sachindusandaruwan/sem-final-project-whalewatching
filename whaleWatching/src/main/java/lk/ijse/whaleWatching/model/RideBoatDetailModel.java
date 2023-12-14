package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.RideBoatDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RideBoatDetailModel {

    public boolean save(RideBoatDto rideBoatDto, Connection dbConnection) throws Exception{

        Connection connection = dbConnection;

        String sql="INSERT INTO ride_boat_detail VALUES(?,?)";
        PreparedStatement pstm= connection.prepareStatement(sql);

        pstm.setString(1, rideBoatDto.getBoat_Id());
        pstm.setString(2, rideBoatDto.getRide_Id());

        return pstm.executeUpdate()>0;

    }

    public long findBoatSeatCount(String rideId) throws SQLException {
        Connection connection= Dbconnection.getInstance().getConnection();
        String sql="SELECT SUM(b.Sheet_Count) AS seats  FROM ride_boat_detail rb, boat b WHERE b.boat_Id = rb.boat_Id AND rb.ride_Id = ?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,rideId);
        ResultSet resultSet=pstm.executeQuery();
        long seatAmount = 0;

        if (resultSet.next()){
            seatAmount = resultSet.getLong(1);

        }
        return seatAmount;
    }
}
