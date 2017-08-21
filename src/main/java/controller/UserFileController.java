package controller;

import dao.UserFileDao;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import util.TokenGenerator;
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
    private final Logger LOG = LoggerFactory.getLogger(UserFileController.class);
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserFileController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public UserFile getUserFile(int id) {
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Files WHERE id = ?")) {
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

    public int getPublicUserFile(String publicLink) {
        try (PreparedStatement ps = con.prepareStatement("SELECT id FROM Files WHERE publicLink = ?")) {
            ps.setString(1, publicLink);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int fileId = rs.getInt("id");
                LOG.info("File found with this publicLink: {}, id: {}", publicLink, fileId);
                return fileId;
            }
        } catch (SQLException e) {
            LOG.error("getPublicLUserFile is failed with Exception", e);
        }
        LOG.debug("File not found with this publicLink: {} in getPublicUserFile method", publicLink);
        return -1;
    }

    public int addNewUserFile(UserFile userFile) {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO Files" +
                "(path, `size`, uploadDate, filename, extension, maxSize, isFolder, ownerId, parentId, label) " +
                "VALUES (?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
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
        try (PreparedStatement ps = con.prepareStatement("UPDATE Files SET filename = ?, maxSize = ?, " +
                "parentId = ?, label = ? WHERE id = ?")) {
            ps.setString(1, userFile.getFileName());
            ps.setDouble(2, userFile.getMaxSize());
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
        try (PreparedStatement ps = con.prepareStatement("UPDATE Files SET `size` = `size` + ? WHERE id = ?")) {
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
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Files WHERE parentid = ?")) {
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
        try (PreparedStatement fkSet = con.prepareStatement("SET foreign_key_checks = ?");
             PreparedStatement deleteFiles = con.prepareStatement("DELETE FROM Files WHERE id = ?");
             PreparedStatement deletePermissions = con.prepareStatement("DELETE FROM Permissions " +
                     "WHERE fileId = ?")) {
            deleteFiles.setInt(1, id);
            deletePermissions.setInt(1, id);
            fkSet.setInt(1, 0);
            fkSet.executeUpdate();
            int successFiles = deleteFiles.executeUpdate();
            int successPermissions = deletePermissions.executeUpdate();
            fkSet.setInt(1, 1);
            fkSet.executeUpdate();
            if ((successFiles > 0) && (successPermissions >= 0)) {
                LOG.info("UserFile is successfully deleted with this id: {}", id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Delete userFile is failed with Exception", e);
            return false;
        }
        LOG.debug("File not found with this id: {} in deleteUserFile method", id);
        return false;
    }

    public int checkUserFile(String filename, String extension, int parentId) {
        try (PreparedStatement ps = con.prepareStatement("SELECT id FROM Files " +
                "WHERE filename = ? AND extension = ? AND parentId = ?")) {
            ps.setString(1, filename);
            ps.setString(2, extension);
            ps.setInt(3, parentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOG.info("UserFile(filename: {}, extension: {}, parentId: {}) is found", filename, extension, parentId);
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOG.error("check UserFile is failed with Exception", e);
        }
        return -1;
    }

    public boolean checkAvailableSpace(int id, double fileSize) {
        try (PreparedStatement ps = con.prepareStatement("SELECT id, `size`, maxSize, isFolder FROM Files WHERE id = ? OR " +
                "(parentId = ? AND isFolder = ?)")) {
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setBoolean(3, true);
            ResultSet rs = ps.executeQuery();
            double maxSize = 0;
            double size= 0;
            while (rs.next()) {
                if(rs.getInt("id") == id) {
                    maxSize = rs.getDouble("maxSize");
                    size = rs.getDouble("size");
                    continue;
                }
                if(rs.getBoolean("isFolder") && rs.getInt("id") != id) {
                    size += rs.getDouble("maxSize");
                }
            }
            if ((maxSize - size) > fileSize) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            LOG.error("checkAvailableSpace is failed with Exception", e);
        }
        return false;
    }

    public boolean setFileSize(int id, double fileSize) {
        try (PreparedStatement ps = con.prepareStatement("UPDATE Files SET `size` = ? WHERE id = ?")) {
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

    public boolean increaseFileSize(int homeId, double increment) {
        try (PreparedStatement ps = con.prepareStatement("UPDATE Files SET `maxSize`= `maxSize` + ? " +
                "WHERE id = ?")) {
            ps.setDouble(1, increment);
            ps.setInt(2, homeId);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("increaseFileSize(increment: {}) is successfully modified with this homeId: {}", increment, homeId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("setFileSize is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} in setFileSize method", homeId);
        return false;
    }

    public boolean setPublicLink(int fileId, int userId) {
        try(PreparedStatement ps = con.prepareStatement("UPDATE Files SET publicLink = ? " +
                "WHERE id = ? AND ownerId = ?")) {
            String token = TokenGenerator.createToken();
            String link = token.substring(0, token.indexOf('-'));
            ps.setString(1, link);
            ps.setInt(2, fileId);
            ps.setInt(3, userId);
            int success = ps.executeUpdate();
            if(success > 0) {
                LOG.info("setPublicLink added successfully to this id: {}, ownerId:{}", fileId, userId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("setPublicLink is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} and ownerId: {} in setPublicLink", fileId, userId);
        return false;
    }

    public boolean deletePublicLink(int fileId, int userId) {
        try(PreparedStatement ps = con.prepareStatement("UPDATE Files SET publicLink = NULL " +
                "WHERE id = ? AND ownerId = ?")) {
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            int success = ps.executeUpdate();
            if(success > 0) {
                LOG.info("deletePublicLink successfully done with this id: {}, ownerId:{}", fileId, userId);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("deletePublicLink is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} and ownerId: {} in deletePublicLink", fileId, userId);
        return false;
    }

    public String getPublicLink(int fileId, int userId) {
        try(PreparedStatement ps = con.prepareStatement("SELECT publicLink FROM Files WHERE id = ? AND ownerId = ?")) {
            ps.setInt(1, fileId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                LOG.info("getPublicLink successfully done with this id: {}, ownerId:{}", fileId, userId);
                return rs.getString("publicLink");
            }
        } catch (SQLException e) {
            LOG.error("getPublicLink is failed with Exception", e);
        }
        LOG.debug("File not found with this id: {} and ownerId: {} in deletePublicLink", fileId, userId);
        return null;
    }
}