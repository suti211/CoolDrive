package controller;

import dao.PermissionsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class PermissionsController extends DatabaseController implements PermissionsDao{

    private final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);

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
            if (success > 0){
                LOG.info("Add file to user is succeeded(fileId: {}, userId: {})",fileId,userId);
                return true;
            }

        } catch (SQLException e) {
            LOG.error("Add file to user is failed with Exception",e);
        }
        LOG.debug("Add file to user is failed(fileId: {}, userId: {} in addFileToUser method",fileId,userId);
        return false;
    }

    public boolean removeFileFromUser(int fileId, int userId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Permissions WHERE fileId = ? AND userId = ?");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            int success = ps.executeUpdate();
            if(success > 0){
                LOG.info("Remove file from user is succeeded(fileId: {}, userId: {})",fileId,userId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Remove file from user is failed with Exception",e);
        }
        LOG.debug("Remove file from user is failed(fileId: {}, userId: {} in removeFileFromUser",fileId,userId);
        return false;
    }

    public boolean checkAccess(int fileId, int userId) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Permissions WHERE fileId = ? AND userId = ?");
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            LOG.error("checkAccess is failed with Exception",e);
        }
        return false;
    }
}
