package controller;

import dao.PermissionsDao;
import util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class PermissionsController extends DatabaseController implements PermissionsDao{

    public PermissionsController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public boolean addFileToUser(int userId, int fileId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO Permissions(userId, fileId) VALUES (?, ?)");
            ps.setInt(1, userId);
            ps.setInt(2, fileId);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFileFromUser(int userId, int fileId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Permissions WHERE userId = ? AND fileId = ?");
            ps.setInt(1, userId);
            ps.setInt(2, fileId);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
