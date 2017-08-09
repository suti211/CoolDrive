package controller;

import dao.PermissionsDao;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class PermissionsController extends DatabaseController implements PermissionsDao {

    private final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public PermissionsController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public boolean addFileToUser(int fileId, int userId, boolean readOnly) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT INTO Permissions(fileId, userId, readOnly) VALUES (?, ?, ?)");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            ps.setBoolean(3, readOnly);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Add file to user is succeeded(fileId: {}, userId: {})", fileId, userId);
                return true;
            }

        } catch (SQLException e) {
            LOG.error("Add file to user is failed with Exception", e);
        }
        LOG.debug("Add file to user is failed(fileId: {}, userId: {} in addFileToUser method", fileId, userId);
        return false;
    }

    public boolean removeFileFromUser(int fileId, int userId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Permissions WHERE fileId = ? AND userId = ?");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Remove file from user is succeeded(fileId: {}, userId: {})", fileId, userId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Remove file from user is failed with Exception", e);
        }
        LOG.debug("Remove file from user is failed(fileId: {}, userId: {} in removeFileFromUser", fileId, userId);
        return false;
    }

    public boolean changeAccess(int fileId, int userId, boolean readOnly) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Permissions SET readOnly = ? WHERE fileId = ? AND userId = ?");
            ps.setBoolean(1, readOnly);
            ps.setInt(2, fileId);
            ps.setInt(3, userId);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Access changed(fileId: {}, userId: {})", fileId, userId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("checkAccess is failed with Exception", e);
        }
        return false;
    }

    public List<UserFile> sharedFiles(String columnName, int value) {
        List<UserFile> userFiles = new ArrayList<>();
        String sql = String.format("SELECT Files.*, Permissions.readOnly FROM Files " +
                "JOIN Permissions ON(Files.id = Permissions.fileId) WHERE %s = ?", columnName);
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userFiles.add(new UserFile(
                        rs.getInt("id"),
                        rs.getString("path"),
                        rs.getDouble("size"),
                        sdf.format(rs.getTimestamp("uploadDate")),
                        rs.getString("filename"),
                        rs.getString("extension"),
                        rs.getDouble("maxSize"),
                        rs.getBoolean("isFolder"),
                        rs.getInt("ownerId"),
                        rs.getInt("parentId"),
                        rs.getString("label"),
                        rs.getBoolean("readOnly")));
            }
        } catch (SQLException e) {
            LOG.error("sharedWithMe is failed with Exception", e);
        }
        return userFiles;
    }

    public boolean checkAccess(int fileId, int userId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT Permissions.* FROM Permissions JOIN Files " +
                    "ON(Files.id = Permissions.fileId) WHERE Permissions.fileId = ? " +
                    "AND (Permissions.userId = ? OR Files.ownerId = ?)");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOG.error("checkAccess is failed with Exception", e);
        }
        return false;
    }
}
