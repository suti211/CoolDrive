package dao;

import dto.Share;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import util.ConnectionUtil;
import util.PropertiesHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class SimplePermissionsDao implements PermissionsDao,AutoCloseable {
    private ConnectionUtil connectionUtil;
    private PropertiesHandler propertiesHandler;
    private Connection con;
    private final Logger LOG = LoggerFactory.getLogger(SimplePermissionsDao.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    public SimplePermissionsDao(ConnectionUtil connectionUtil, PropertiesHandler propertiesHandler) {
        this.connectionUtil = connectionUtil;
        this.propertiesHandler = propertiesHandler;
        con = connectionUtil.getConnection(propertiesHandler.getDATABASENAME());
    }

    @Override
    public boolean addFileToUser(int fileId, int userId, boolean readOnly) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO Permissions(fileId, userId, readOnly) " +
                "SELECT id, ?, ? FROM Files WHERE parentId = ? OR id = ?")) {
            ps.setInt(1, userId);
            ps.setBoolean(2, readOnly);
            ps.setInt(3, fileId);
            ps.setInt(4, fileId);
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

    @Override
    public boolean removeFileFromUser(int fileId, int userId) {
        try (PreparedStatement ps = con.prepareStatement("DELETE FROM Permissions " +
                "WHERE fileId IN (SELECT id FROM Files WHERE parentId = ? OR id = ?) AND userId = ?")) {
            ps.setInt(1, fileId);
            ps.setInt(2, fileId);
            ps.setInt(3, userId);
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

    @Override
    public boolean changeAccess(int fileId, int userId, boolean readOnly) {
        try (PreparedStatement ps = con.prepareStatement("UPDATE Permissions SET readOnly = ? " +
                "WHERE fileId IN (SELECT id from Files WHERE parentId = ? OR id = ?) AND userId = ?")) {
            ps.setBoolean(1, readOnly);
            ps.setInt(2, fileId);
            ps.setInt(3, fileId);
            ps.setInt(4, userId);
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

    @Override
    public List<UserFile> sharedFiles(String columnName, int value) {
        List<UserFile> userFiles = new ArrayList<>();
        String sql;
        if(columnName.equalsIgnoreCase("Files.parentId")) {
            sql = String.format("SELECT Files.*, Permissions.readOnly FROM Files JOIN Permissions ON" +
                    "(Files.id = Permissions.fileId) WHERE %s = ?", columnName);
        } else {
            sql = String.format("SELECT * FROM " +
                    "(SELECT Files.*, Permissions.readOnly FROM Files JOIN Permissions ON" +
                    "(Files.id = Permissions.fileId) WHERE %s = ?) AS all_files " +
                    "WHERE parentId NOT IN (SELECT Permissions.fileId FROM Permissions);", columnName);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
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

    public List<Share> sharedWith(int fileId) {
        List<Share> shares = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement("SELECT Users.email, Permissions.readOnly FROM Users " +
                "JOIN Permissions ON(Users.id = Permissions.userId) WHERE fileId =  ?")) {
            ps.setInt(1, fileId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                shares.add(new Share(rs.getString("email"), rs.getBoolean("readOnly")));
            }
        } catch (SQLException e) {
            LOG.error("sharedWith is failed with Exception", e);
        }
        return shares;
    }

    @Override
    public boolean checkAccess(int fileId, int userId) {
        try (PreparedStatement ps = con.prepareStatement("SELECT Permissions.* FROM Permissions JOIN Files " +
                "ON(Files.id = Permissions.fileId) WHERE Permissions.fileId = ? " +
                "AND (Permissions.userId = ? OR Files.ownerId = ?)")) {
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

    @Override
    public void close(){
        try {
            this.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
