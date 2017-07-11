package controller;

import dao.UserFileDao;
import dto.UserFile;
import util.ConnectionUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class UserFileController extends DatabaseController implements UserFileDao {

    public UserFileController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public UserFile getUserFile(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return new UserFile(rs.getInt("id"), rs.getString("path"),
                    rs.getDouble("size"), rs.getDate("uploadDate"),
                    rs.getString("filename"), rs.getDouble("maxSize"),
                    rs.getBoolean("isFolder"), rs.getInt("ownerId"), rs.getInt("parentId"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addNewUserFile(UserFile userFile) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO Files(path, `size`, uploadDate, filename, maxSize, isFolder, ownerId, parentId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, userFile.getPath());
            ps.setDouble(2, userFile.getSize());
            ps.setDate(3, userFile.getUploadDate());
            ps.setString(4, userFile.getFileName());
            ps.setDouble(5, userFile.getMaxSize());
            ps.setBoolean(6, userFile.isFolder());
            ps.setInt(7, userFile.getOwnerId());
            ps.setInt(8, userFile.getParentId());
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modifyUserFile(int id, UserFile userFile) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Files SET path = ?, filename = ?, ownerId = ?, parentId = ? WHERE id = ?");
            ps.setString(1, userFile.getPath());
            ps.setString(2, userFile.getFileName());
            ps.setInt(3, userFile.getOwnerId());
            ps.setInt(5, userFile.getParentId());
            ps.setInt(5, id);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeFolderSize(int id, double maxSize) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Files SET maxSize = ? WHERE id = ?");
            ps.setDouble(1, maxSize);
            ps.setInt(2, id);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserFile(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Permissions WHERE id = ?");
            ps.setInt(1, id);
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkUserFile(UserFile userFile) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT id FROM Files WHERE path = ? AND filename = ?");
            ps.setString(1, userFile.getPath());
            ps.setString(2, userFile.getFileName());
            int success = ps.executeUpdate();
            return success > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
