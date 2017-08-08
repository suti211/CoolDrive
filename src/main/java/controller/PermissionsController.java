package controller;

import dao.PermissionsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class PermissionsController extends DatabaseController implements PermissionsDao{

    private final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);

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
            if (success > 0){
                LOG.info("Add file to user is succeeded(fileId: {}, userId: {)",fileId,userId);
                return true;
            }

        } catch (SQLException e) {
            LOG.error("Add file to user is failed with Exception",e);
        }
        LOG.debug("Add file to user is failed(fileId: {), userId: {} in addFiletoUser method",fileId,userId);
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
                LOG.info("Remove file frome user is succeeded(fileId: {}, userId: {)",fileId,userId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Remove file from user is failed with Exception",e);
        }
        LOG.debug("Remove file from user is failed(fileId: {), userId: {} in removeFileFromUser",fileId,userId);
        return false;
    }
}
