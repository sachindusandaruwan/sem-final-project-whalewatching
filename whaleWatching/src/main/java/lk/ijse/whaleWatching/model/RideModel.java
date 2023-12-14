package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.RideBoatDto;
import lk.ijse.whaleWatching.dto.RideDto;
import lk.ijse.whaleWatching.dto.tm.RideBoatTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RideModel {
    public boolean saveRide(RideDto dto) throws SQLException {
        System.out.println(dto);
        Connection connection= Dbconnection.getInstance().getConnection();

        RideBoatDetailModel rideBoatDetailModel = new RideBoatDetailModel();
        connection.setAutoCommit(false);
        try {

            String sql="INSERT INTO ride VALUES(?,?,?,?,?,?)";
            PreparedStatement pstm= connection.prepareStatement(sql);

            pstm.setString(1, dto.getRide_Id());
            pstm.setDouble(2,dto.getPrice());
            pstm.setString(3, dto.getType());
            pstm.setString(4, dto.getDate());
            pstm.setString(5, dto.getTime());
            pstm.setString(6, dto.getStatus());

            boolean isSaved = pstm.executeUpdate()>0;
            System.out.println(isSaved);

            if (isSaved) {

                for (RideBoatTm tm : dto.getRideBoatTms()) {

                    RideBoatDto rideBoatDto = new RideBoatDto(tm.getBoat_id(), dto.getRide_Id());
                    boolean isAdded = rideBoatDetailModel.save(rideBoatDto, connection);

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


    public RideDto searchRide(String rideId) throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM ride WHERE ride_id=?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, rideId);

        ResultSet resultSet = pstm.executeQuery();
        RideDto dto = null;

        if (resultSet.next()) {
            String id=resultSet.getString(1);
            double price=resultSet.getDouble(2);
            String type=resultSet.getString(3);
            String date=resultSet.getString(4);
            String time=resultSet.getString(5);
            String status=resultSet.getString(6);

            dto=new RideDto(id,price,type,date,time,status);

        }
        return dto;
    }

    public boolean deleteRide(String rId) throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="DELETE FROM ride WHERE ride_Id=?";
        PreparedStatement pstm= connection.prepareStatement(sql);
        pstm.setString(1,rId);

        boolean isDeleted=pstm.executeUpdate()>0;

        return isDeleted;
    }

    public boolean updateRide(RideDto dto) throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="UPDATE ride SET one_Person_Price=?,typ=?,date=?,time=?,status=? WHERE ride_Id=?";

        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setDouble(1,dto.getPrice());
        pstm.setString(2, dto.getType());
        pstm.setString(3, dto.getDate());
        pstm.setString(4, dto.getTime());
        pstm.setString(5, dto.getStatus());

        pstm.setString(6, dto.getRide_Id());

        boolean isUpadate=pstm.executeUpdate()>0;
        return isUpadate;


    }

    public List<RideDto> getAllRides() throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM ride";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<RideDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String rideId = resultSet.getString(1);
            double price = resultSet.getDouble(2);
            String type = resultSet.getString(3);
            String date=resultSet.getString(4);
            String time=resultSet.getString(5);
            String status=resultSet.getString(6);

            var dto = new RideDto(rideId,price,type,date,time, status);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<RideDto> loadAllRides() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM ride";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<RideDto> rideList = new ArrayList<>();

        while (resultSet.next()) {
            rideList.add(new RideDto(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
                    ));
        }
        return rideList;
    }

    public List<String> findAllIds() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT ride_Id FROM ride";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> rideList = new ArrayList<>();

        while (resultSet.next()) {
            rideList.add(resultSet.getString(1));
        }
        return rideList;
    }
}
