package lk.ijse.whaleWatching.model;

import lk.ijse.whaleWatching.db.Dbconnection;
import lk.ijse.whaleWatching.dto.BoatDto;
import lk.ijse.whaleWatching.dto.CustomerDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoatModel {
    public static boolean deleteBoat(String bId) throws SQLException {
        boolean isDeleted=false;
        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="DELETE FROM boat WHERE boat_Id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,bId);
        isDeleted=pstm.executeUpdate()>0;
        return isDeleted;


    }

    public static List<BoatDto> loadAllBoats() throws SQLException {

            Connection connection = Dbconnection.getInstance().getConnection();

            String sql = "SELECT * FROM boat";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

            List<BoatDto> boatList = new ArrayList<>();

            while (resultSet.next()) {
                boatList.add(new BoatDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)
                ));
            }
            return boatList;
        }

   /* public static String generateNextOrderId() {


    }*/


    public List<BoatDto> getAllBoat() throws SQLException {
        Connection connection=Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM boat";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<BoatDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String BoatID = resultSet.getString(1);
            String BoatName = resultSet.getString(2);
            String BoatType = resultSet.getString(3);
            int Sheet_Count = resultSet.getInt(4);
            String Description = resultSet.getString(5);

            var dto = new BoatDto(BoatID,BoatName,BoatType,Sheet_Count,Description);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public List<BoatDto> loadAllBoat() throws SQLException {
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT * FROM boat";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<BoatDto> boatList = new ArrayList<>();

        while (resultSet.next()) {
            boatList.add(new BoatDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));
        }
        return boatList;
    }

    public boolean saveBoat(final BoatDto dto) throws SQLException {
        boolean isSaved=false;

            Connection connection = Dbconnection.getInstance().getConnection();

            String sql = "INSERT INTO boat VALUES(?,?,?,?,?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getB_Id());
            pstm.setString(2, dto.getB_Name());
            pstm.setString(3, dto.getB_Type());
            pstm.setInt(4, dto.getSheet_Count());
            pstm.setString(5, dto.getDescription());

            isSaved = pstm.executeUpdate() > 0;

            return isSaved;
    }

    public boolean updateBoat(BoatDto dto) throws SQLException {

        boolean isUpadate=false;

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="UPDATE boat SET boat_Name=?,boat_Type=?,Sheet_Count=?,description=? WHERE boat_Id=?";

        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1, dto.getB_Name());
        pstm.setString(2, dto.getB_Type());
        pstm.setInt(3,dto.getSheet_Count());
        pstm.setString(4, dto.getDescription());
        pstm.setString(5, dto.getB_Id());

        isUpadate=pstm.executeUpdate()>0;
        return isUpadate;
    }

    public BoatDto searchBoat(String BId) throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="SELECT * FROM boat WHERE boat_Id=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,BId);
        ResultSet resultSet=pstm.executeQuery();
        BoatDto dto=null;

        if (resultSet.next()){
            String bID=resultSet.getString(1);
            String bName=resultSet.getString(2);
            String bType=resultSet.getString(3);
            int SheetCount=resultSet.getInt(4);
            String Des=resultSet.getString(5);

            dto=new BoatDto(bID,bName,bType,SheetCount,Des);

        }
        return dto;
    }

    public List<String> findAllIds() throws Exception{
        Connection connection = Dbconnection.getInstance().getConnection();

        String sql = "SELECT boat_Id FROM boat";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> boatList = new ArrayList<>();

        while (resultSet.next()) {
            boatList.add(resultSet.getString(1));
        }
        return boatList;
    }


    public BoatDto searchBoatName(String bname) throws SQLException {

        Connection connection=Dbconnection.getInstance().getConnection();
        String sql="SELECT * FROM boat WHERE boat_Name=?";
        PreparedStatement pstm=connection.prepareStatement(sql);

        pstm.setString(1,bname);
        ResultSet resultSet=pstm.executeQuery();
        BoatDto dto=null;

        if (resultSet.next()){
            String bID=resultSet.getString(1);
            String bName=resultSet.getString(2);
            String bType=resultSet.getString(3);
            int SheetCount=resultSet.getInt(4);
            String Des=resultSet.getString(5);

            dto=new BoatDto(bID,bName,bType,SheetCount,Des);

        }
        return dto;
    }
}
