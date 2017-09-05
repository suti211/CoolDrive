package service;

import dao.SimplePermissionsDao;
import dao.SimpleUserDao;
import dao.SimpleUserFileDao;
import dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ControllersFactory;

import java.util.List;

/**
 * Created by David Szilagyi on 2017. 08. 07..
 */
@Service
public class SimplePermissionService implements PermissionService {
    private ControllersFactory controllersFactory;
    @Autowired
    public SimplePermissionService(ControllersFactory controllersFactory) {
        this.controllersFactory = controllersFactory;
    }

    private final Logger LOG = LoggerFactory.getLogger(SimpleRegisterService.class);

    private User getUser(String email) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            return simpleUserDao.getUser("email", email);
        }
    }

    @Override
    public Status add(Share share) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController();
             SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            User user = getUser(share.getEmail());
            int fileId = share.getToken().getId();
            String status;
            if (user != null) {
                if (simpleUserFileDao.getUserFile(fileId).getOwnerId() != user.getId()) {
                    if (permissionsController.addFileToUser(fileId, user.getId(), share.isReadOnly())) {
                        status = String.format("Successfully shared this file with %s", share.getEmail());
                        LOG.info("Share - Add share file method is success(email: {}, id: {})", share.getEmail(), share.getToken().getId());
                        return new Status(Operation.SHARE, true, status);
                    }
                    LOG.error("Share - Add share file method is failed(email: {}, id: {})", share.getEmail(), share.getToken().getId());
                    status = String.format("This file is already shared with this user: %s", share.getEmail());
                    return new Status(Operation.SHARE, false, status);
                }LOG.debug("Share - Add share file method is failed because cannot share files with owner(email: {}, id: {}", share.getEmail(), share.getToken().getId());
                status = String.format("You cannot share item with yourself!");
                return new Status(Operation.SHARE, false, status);

            }
            LOG.debug("Share - Add share file method is failed because no user found(email: {}, id: {}", share.getEmail(), share.getToken().getId());
            status = String.format("User not found with this email: %s", share.getEmail());
            return new Status(Operation.SHARE, false, status);
        }
    }

    @Override
    public Status remove(Share share) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController()) {
            User user = getUser(share.getEmail());
            String status;
            if (user != null) {
                if (permissionsController.removeFileFromUser(share.getToken().getId(), user.getId())) {
                    LOG.info("Share - Remove file method is success(email: {}, id: {})", share.getEmail(), share.getToken().getId());
                    status = String.format("Successfully removed share from %s", share.getEmail());
                    return new Status(Operation.SHARE, true, status);
                }
                LOG.error("Share - Remove file method is failed(email: {}, id: {})", share.getEmail(), share.getToken().getId());
                status = String.format("Failed to remove permission from %s", share.getEmail());
                return new Status(Operation.SHARE, false, status);
            }
            LOG.debug("Share - Remove file method is failed because no user found(email: {}, id: {}", share.getEmail(), share.getToken().getId());
            status = String.format("User not found with this email: %s", share.getEmail());
            return new Status(Operation.SHARE, false, status);
        }
    }


    @Override
    public Status changeAccess(Share share) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController()) {
            User user = getUser(share.getEmail());
            String status;
            if (user != null) {
                if (permissionsController.changeAccess(share.getToken().getId(), user.getId(), share.isReadOnly())) {
                    LOG.info("Share - change access method is success(email: {}, id: {}", share.getEmail(), share.getToken().getId());
                    status = String.format("Access changed to this email: %s", share.getEmail());
                    return new Status(Operation.SHARE, true, status);
                }
                LOG.error("Share - change access method is failed(email: {}, id: {}", share.getEmail(), share.getToken().getId());
                status = String.format("Access changed failed to this email: %s", share.getEmail());
                return new Status(Operation.SHARE, false, status);
            }
            LOG.debug("Share - change access method is failed because no user found(email: {}, id: {}", share.getEmail(), share.getToken().getId());
            status = String.format("User not found with this email: %s", share.getEmail());
            return new Status(Operation.SHARE, false, status);
        }
    }

    @Override
    public List<UserFile> sharedWithMe(Token token) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController();
             SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            LOG.info("sharedWithMe method called with userId: {}", userId);
            if (token.getId() != -1) {
                return permissionsController.sharedFiles("Files.parentId", token.getId());
            } else {
                return permissionsController.sharedFiles("Permissions.userId", userId);
            }
        }
    }

    @Override
    public List<UserFile> sharedFiles(Token token) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController();
             SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            LOG.info("sharedFiles method called with userId: {}", userId);
            return permissionsController.sharedFiles("Files.ownerId", userId);
        }
    }


    @Override
    public List<UserFile> sharedFolder(Token token) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController();
             SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            int folderId = token.getId();
            LOG.info("sharedFolderFiles method called with userId: {}", userId);
            return permissionsController.sharedFiles("Files.parentId", folderId);
        }
    }

    @Override
    public List<Share> sharedWith(Token token) {
        try (SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController();
             SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            LOG.info("sharedWith method called with userId: {}", userId);
            return permissionsController.sharedWith(token.getId());
        }
    }
}
