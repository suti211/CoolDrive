package test;

import controller.DatabaseController;
import util.ConnectionUtil;
import util.PathUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mudzso on 2017.08.02..
 */
public class TestDbManager {
    private DatabaseController databaseController;
    private String databaseName;

    private Connection connection;

    public TestDbManager(ConnectionUtil.DatabaseName database) {
        databaseName = database.toString();
        databaseController = new DatabaseController(database);
        connection = databaseController.getCon();
    }

    public void clearDataBase(String tableName){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("DELETE FROM " + tableName);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillUsersTableWithDumbData(int number){
        PreparedStatement ps = null;
        try {
            for (int i = 0; i <number ; i++) {


            ps = connection.prepareStatement("INSERT INTO Users(id,username, pass, email, validated, firstname, lastname, admin, token, registerdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)");
            ps.setInt(1,i);
            ps.setString(2, "testUser"+i);
            ps.setString(3, "pass");
            ps.setString(4, "testUser@gmail.com"+i);
            ps.setBoolean(5, false);
            ps.setString(6, "test");
            ps.setString(7, "user");
            ps.setBoolean(8, false);
            ps.setString(9, "asd"+i);
            ps.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void fillFilesTableWithDumbData(int number,int userId){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("INSERT INTO files(id, path, `size`, uploadDate, filename, extension, maxSize, isFolder, ownerId, parentId, label) VALUES (?,?, ?, NOW(), ?, ?, ?, ?, ?,NULL , ?)");
            ps.setInt(1,1);
            ps.setString(2, PathUtil.ROOT_PATH);
            ps.setDouble(3, 10);
            ps.setString(4, "testFolder");
            ps.setString(5, "dir");
            ps.setDouble(6, 1000);
            ps.setBoolean(7, true);
            ps.setInt(8, userId);
            ps.setString(9, null);
            ps.executeUpdate();
            for (int i = 2; i < number+2; i++) {
                ps = connection.prepareStatement("INSERT INTO Files(id, path, `size`, uploadDate, filename, extension, maxSize, isFolder, ownerId, parentId, label) VALUES (?,?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?)");
                ps.setInt(1,i);
                ps.setString(2, PathUtil.ROOT_PATH);
                ps.setDouble(3, 10);
                ps.setString(4, "asd"+i);
                ps.setString(5, "txt");
                ps.setDouble(6, 0);
                ps.setBoolean(7, false);
                ps.setInt(8, userId);
                ps.setInt(9, 1);
                ps.setString(10, null);
                ps.executeUpdate();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void fillTransactionsTableWithDumbData(int from,int number,int userId){
        PreparedStatement ps = null;


        try {
            for (int i = from; i < number+from; i++) {


                ps = connection.prepareStatement("INSERT INTO Transactions(id,userId, firstname, lastname, zip, city, address1, address2, phone, bought, boughtDate) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE )");

                ps.setInt(1, i);
                ps.setInt(2, userId);
                ps.setString(3, "test"+i);
                ps.setString(4, "user"+i);
                ps.setString(5, "3525");
                ps.setString(6, "miskolc");
                ps.setString(7, "codecool1");
                ps.setString(8, "codecool2");
                ps.setString(9, "063098543");
                ps.setString(10, "100");
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }





}
