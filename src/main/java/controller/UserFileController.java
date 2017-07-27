package controller;

import dao.UserFileDao;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class UserFileController extends DatabaseController implements UserFileDao {
    private static final Logger LOG = LoggerFactory.getLogger(UserFileController.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserFileController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public UserFile getUserFile(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Files WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOG.info("File(filename: {}, path: {}) found with this id: {}", rs.getString("filename"), rs.getString("path"), id);
                return new UserFile(
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
                        rs.getString("label")
                );
            }
        } catch (SQLException e) {
            LOG.error("Get user file is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} in getUserFile method", id);
        return null;
    }

    public int addNewUserFile(UserFile userFile) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO Files(path, `size`, uploadDate, filename, extension, maxSize, isFolder, ownerId, parentId, label) VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userFile.getPath());
            ps.setDouble(2, userFile.getSize());
            ps.setString(3, userFile.getFileName());
            ps.setString(4, userFile.getExtension());
            ps.setDouble(5, userFile.getMaxSize());
            ps.setBoolean(6, userFile.isFolder());
            ps.setInt(7, userFile.getOwnerId());
            ps.setInt(8, userFile.getParentId());
            ps.setString(9, userFile.getLabel());
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Add new file(filename: {}, path: {}) is successfully created", userFile.getFileName(), userFile.getPath());
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("Add new userfile is failed with Exception", e);
        }
        return -1;
    }

    public boolean modifyUserFile(UserFile userFile) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Files SET path = ?, filename = ?, parentId = ?, label = ? WHERE id = ?");
            ps.setString(1, userFile.getPath());
            ps.setString(2, userFile.getFileName());
            ps.setInt(3, userFile.getParentId());
            ps.setString(4, userFile.getLabel());
            ps.setInt(5, userFile.getId());
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Userfile(filename: {}, path: {}) is successfully modified with this id: {}", userFile.getFileName(), userFile.getPath(), userFile.getId());
                return true;
            }
        } catch (SQLException e) {
            LOG.error("modify userfile is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} in modifyUserFile method", userFile.getId());
        return false;
    }

    public boolean changeFolderCurrSize(int id, double size) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Files SET `size` = `size` + ? WHERE id = ?");
            ps.setDouble(1, size);
            ps.setInt(2, id);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Userfile(size: {}) is successfully modified with this id: {}", size, id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("modify userfile is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} in modifyUserFile method", id);
        return false;
    }

    public List<UserFile> getAllFilesFromFolder(int parentId) {
        List<UserFile> userFiles = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Files WHERE parentid = ?");
            ps.setInt(1, parentId);
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
                        rs.getString("label")));
            }
            LOG.info("Folder found with this id: {}", parentId);

        } catch (SQLException e) {
            LOG.error("getAllFilesFromFolder is failed with Exception", e);
        }
        LOG.debug("Folder not found with this id: {} in getAllFilesFromFolder method", parentId);
        return userFiles;
    }

    public boolean deleteUserFile(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Files WHERE id = ?");
            ps.setInt(1, id);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Userfile is successfully deleted with this id: {}", id);
                return true;

            }
        } catch (SQLException e) {
            LOG.error("Delete userfile is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} in deleteUserFile method", id);
        return false;
    }

    public int checkUserFile(UserFile userFile) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT id FROM Files WHERE path = ? AND filename = ?");
            ps.setString(1, userFile.getPath());
            ps.setString(2, userFile.getFileName());
            int success = ps.executeUpdate();
            if (success > 0) {
                ResultSet rs = ps.executeQuery();
                LOG.info("Userfile(filename: {}, path: {}) is successfully checked", userFile.getFileName(), userFile.getPath());
                rs.first();
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOG.error("check userfile is failed with Exception", e);
        }
        return 0;
    }

    public boolean checkAvailableSpace(int id, double fileSize) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT `size`, maxSize FROM Files WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                if((rs.getDouble("maxSize") - rs.getDouble("size")) > fileSize) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            LOG.error("checkAvailableSpace is failed with Exception", e);
        }
        return false;
    }

    public boolean setFileSize(int id, double fileSize) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Files SET `size` = ? WHERE id = ?");
            ps.setDouble(1, fileSize);
            ps.setInt(2, id);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("setFileSize(size: {}) is successfully modified with this id: {}", fileSize, id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("setFileSize is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} in setFileSize method", id);
        return false;
    }
}