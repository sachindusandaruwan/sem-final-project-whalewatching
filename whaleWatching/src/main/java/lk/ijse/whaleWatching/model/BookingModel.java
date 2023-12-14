package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.BookingCustomDto;
import lk.ijse.whaleWatching.dto.BookingDto;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {

    @SneakyThrows
    public static String getMail(String value) {
        Connection connection = Dbconnection.getInstance().getConnection();
        String sql = "SELECT e_mail FROM customer WHERE cus_Id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,value);
        ResultSet resultSet = pstm.executeQuery();
        String mail = "";
        if (resultSet.next()){
            mail = resultSet.getString(1);
        }
        return mail;
    }

    public static long bookedSheets(String rideId) throws SQLException {
        Connection connection= Dbconnection.getInstance().getConnection();
        String sql=" SELECT COUNT(b.booking_Id) FROM booking b, ride r WHERE b.ride_Id=r.ride_Id AND r.ride_Id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,rideId);
        ResultSet resultSet=pstm.executeQuery();
        long seatAmount = 0;

        if (resultSet.next()){
            seatAmount = resultSet.getLong(1);

        }
        return seatAmount;
    }



    public boolean save(BookingDto bookingDto) throws SQLException {

        Connection connection = Dbconnection.getInstance().getConnection();
        PaymentModel paymentModel = new PaymentModel();
        connection.setAutoCommit(false);
        try {
            boolean isPaymentAdded = paymentModel.save(bookingDto.getPaymentDto(), connection);
            if (isPaymentAdded) {
                String sql = "INSERT INTO booking VALUES(?,?,?,?,?,?)";
                PreparedStatement pstm = connection.prepareStatement(sql);

                pstm.setString(1, bookingDto.getBooking_id());
                pstm.setString(2, bookingDto.getRide_id());
                pstm.setString(3, bookingDto.getCus_id());
                pstm.setString(4, bookingDto.getPay_id());
                pstm.setString(5, bookingDto.getDate());
                pstm.setString(6, bookingDto.getTime());

                boolean isBooking = pstm.executeUpdate() > 0;
                if (!isBooking) {
                    connection.rollback();
                    return false;
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
        }finally {
            connection.setAutoCommit(true);
        }
    }

   /* public List<BookingCustomDto> findAll() throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT r.typ,c.name, b.date, b.time,p.amount FROM booking b, ride r, customer c, payment p WHERE r.ride_Id=b.ride_Id \n" +
                "AND c.cus_Id=b.cus_Id AND p.pay_Id=b.pay_Id ORDER BY b.booking_Id";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<BookingCustomDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String bookingType = resultSet.getString(1);
            String customerName = resultSet.getString(2);
            String bookingDate = resultSet.getString(3);
            String  bookingTime = resultSet.getString(4);
            double amount = resultSet.getDouble(5);

            var dto = new BookingCustomDto(bookingType, customerName, bookingDate, bookingTime, amount);
            dtoList.add(dto);
        }
        return dtoList;
    }*/

    public List<BookingCustomDto> getAllBooking() throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT  b.date, b.time,c.name,r.typ,p.amount FROM booking b, ride r, customer c, payment p WHERE r.ride_Id=b.ride_Id \n" +
                "AND c.cus_Id=b.cus_Id AND p.pay_Id=b.pay_Id ORDER BY b.booking_Id";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<BookingCustomDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String bookingDate=resultSet.getString(1);
            String  bookingTime = resultSet.getString(2);
            //String bookingType = resultSet.getString(4);
            String customerName = resultSet.getString(3);
            String bookingType = resultSet.getString(4);
           // String bookingDate = resultSet.getString(1);
           // String  bookingTime = resultSet.getString(2);
            double amount = resultSet.getDouble(5);

            var dto = new BookingCustomDto(bookingDate, bookingTime, customerName, bookingType, amount);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
