package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean getUser(String userName, String password) throws SQLException {
        Connection connection= Dbconnection.getInstance().getConnection();

        String sql="SELECT * FROM user WHERE userName=? AND password=?";

        PreparedStatement pstm=connection.prepareStatement(sql);
        pstm.setString(1,userName);
        pstm.setString(2,password);

        ResultSet resultSet=pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }
}
