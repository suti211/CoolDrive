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

    public boolean addFileToUser(int fileId, int userId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO Permissions(fileId, userId) VALUES (?, ?)");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFileFromUser(int fileId, int userId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Permissions WHERE fileId = ? AND userId = ?");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
