package service;

import controller.PermissionsController;
import controller.UserController;
import controller.UserFileController;
import dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.SimpleControllersFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 08. 07..
 */
@Path("/share")
public class PermissionService extends SimpleControllersFactory {

    private final Logger LOG = LoggerFactory.getLogger(RegisterService.class);

    private User getUser(String email) {
        try (UserController userController = getUserController()) {
            return userController.getUser("email", email);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Status add(Share share, @Context HttpServletRequest request) {
        try (PermissionsController permissionsController = getPermissionsController();
             UserFileController userFileController = getUserFileController()) {
            User user = getUser(share.getEmail());
            int fileId = share.getToken().getId();
            String status;
            if (user != null) {
                if (userFileController.getUserFile(fileId).getOwnerId() != user.getId()) {
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Status remove(Share share, @Context HttpServletRequest request) {
        try (PermissionsController permissionsController = getPermissionsController()) {
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/changeAccess")
    public Status changeAccess(Share share, @Context HttpServletRequest request) {
        try (PermissionsController permissionsController = getPermissionsController()) {
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedWithMe")
    public List<UserFile> sharedWithMe(Token token) {
        try (PermissionsController permissionsController = getPermissionsController();
             UserController userController = getUserController()) {
            int userId = userController.getUser("token", token.getToken()).getId();
            LOG.info("sharedWithMe method called with userId: {}", userId);
            if (token.getId() != -1) {
                return permissionsController.sharedFiles("Files.parentId", token.getId());
            } else {
                return permissionsController.sharedFiles("Permissions.userId", userId);
            }
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedFiles")
    public List<UserFile> sharedFiles(Token token) {
        try (PermissionsController permissionsController = getPermissionsController();
             UserController userController = getUserController()) {
            int userId = userController.getUser("token", token.getToken()).getId();
            LOG.info("sharedFiles method called with userId: {}", userId);
            return permissionsController.sharedFiles("Files.ownerId", userId);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedFolder")
    public List<UserFile> sharedFolder(Token token) {
        try (PermissionsController permissionsController = getPermissionsController();
             UserController userController = getUserController()) {
            int userId = userController.getUser("token", token.getToken()).getId();
            int folderId = token.getId();
            LOG.info("sharedFolderFiles method called with userId: {}", userId);
            return permissionsController.sharedFiles("Files.parentId", folderId);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedWith")
    public List<Share> sharedWith(Token token) {
        try (PermissionsController permissionsController = getPermissionsController();
             UserController userController = getUserController()) {
            int userId = userController.getUser("token", token.getToken()).getId();
            LOG.info("sharedWith method called with userId: {}", userId);
            return permissionsController.sharedWith(token.getId());
        }
    }
}
