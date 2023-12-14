package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.DashboardCommonDto;
import lk.ijse.whaleWatching.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardModel {

    public DashboardCommonDto getDashboardData() throws SQLException {
        Connection connection= Dbconnection.getInstance().getConnection();
        String sql="SELECT COUNT(c.cus_Id) FROM customer c";
        PreparedStatement pstm= connection.prepareStatement(sql);

        ResultSet resultSet= pstm.executeQuery();
        DashboardCommonDto dashboardCommonDto = new DashboardCommonDto();
        if (resultSet.next()){
            int customerCount=resultSet.getInt(1);

            dashboardCommonDto.setAllCustomerCount(customerCount);
        }

        sql = "SELECT COUNT(e.emp_Id) FROM employee e";
        pstm= connection.prepareStatement(sql);
        resultSet= pstm.executeQuery();
        if (resultSet.next()){
            int employeeCount=resultSet.getInt(1);

            dashboardCommonDto.setAllEmployeeCount(employeeCount);
        }

        sql = "SELECT COUNT(b.boat_Id) FROM boat b";
        pstm= connection.prepareStatement(sql);
        resultSet= pstm.executeQuery();
        if (resultSet.next()){
            int boatCount=resultSet.getInt(1);

            dashboardCommonDto.setAllBoatsCount(boatCount);
        }

        sql = "SELECT COUNT(bo.booking_Id) FROM booking bo";
        pstm= connection.prepareStatement(sql);
        resultSet= pstm.executeQuery();
        if (resultSet.next()){
            int bookingCount=resultSet.getInt(1);

            dashboardCommonDto.setAllBookingCount(bookingCount);
        }

        sql = "SELECT COUNT(b.booking_Id) FROM booking b, ride r WHERE b.ride_Id=r.ride_Id AND r.typ='Normal'";
        pstm= connection.prepareStatement(sql);
        resultSet= pstm.executeQuery();
        if (resultSet.next()){
            int bookingNCount=resultSet.getInt(1);

            dashboardCommonDto.setNormalBookingCount(bookingNCount);
        }

        sql = "SELECT COUNT(b.booking_Id) FROM booking b, ride r WHERE b.ride_Id=r.ride_Id AND r.typ='Luxury'";
        pstm= connection.prepareStatement(sql);
        resultSet= pstm.executeQuery();
        if (resultSet.next()){
            int bookingLCount=resultSet.getInt(1);

            dashboardCommonDto.setLuxuryBookingCount(bookingLCount);
        }

        return dashboardCommonDto;
    }
}
