package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.BoatDto;
import lk.ijse.whaleWatching.dto.PaymentDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentModel {

    public boolean save(final PaymentDto paymentDto, Connection dbConnection) throws SQLException {
        boolean isSaved=false;

        Connection connection = dbConnection;

        String sql = "INSERT INTO payment VALUES(?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, paymentDto.getPay_id());
        pstm.setString(2, paymentDto.getDate());
        pstm.setDouble(3, paymentDto.getAmount());

        isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

}
