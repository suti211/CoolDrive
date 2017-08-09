package service;

import dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ControllersFactory;
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
public class PermissionService extends ControllersFactory {

    private final Logger LOG = LoggerFactory.getLogger(RegisterService.class);

    private User getUser(String email) {
        return userController.getUser("email", email);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Status add(Share share, @Context HttpServletRequest request) {
        User user = getUser(share.getEmail());
        String status;
        if (user != null) {
            if (permissionsController.addFileToUser(share.getToken().getId(), user.getId(), share.isReadOnly())) {
                status = String.format("Successfully added permission to %s", share.getEmail());
                LOG.info("Share - Add share file method is success(email: {}, id: {})",share.getEmail(), share.getToken().getId());
                return new Status(Operation.SHARE, true, status);
            }
            LOG.error("Share - Add share file method is failed(email: {}, id: {})",share.getEmail(), share.getToken().getId());
            status = String.format("This file is already shared with this user: %s", share.getEmail());
            return new Status(Operation.SHARE, false, status);
        }
        LOG.debug("Share - Add share file method is failed because no user found(email: {}, id: {}",share.getEmail(), share.getToken().getId());
        status = String.format("User not found with this email: %s", share.getEmail());
        return new Status(Operation.SHARE, false, status);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Status remove(Share share, @Context HttpServletRequest request) {
        User user = getUser(share.getEmail());
        String status;
        if (user != null) {
            if (permissionsController.removeFileFromUser(share.getToken().getId(), user.getId())) {
                LOG.info("Share - Remove file method is success(email: {}, id: {})",share.getEmail(), share.getToken().getId());
                status = String.format("Successfully removed permission from %s", share.getEmail());
                return new Status(Operation.SHARE, true, status);
            }
            LOG.error("Share - Remove file method is failed(email: {}, id: {})",share.getEmail(), share.getToken().getId());
            status = String.format("Failed to remove permission from %s", share.getEmail());
            return new Status(Operation.SHARE, false, status);
        }
        LOG.debug("Share - Remove file method is failed because no user found(email: {}, id: {}",share.getEmail(), share.getToken().getId());
        status = String.format("User not found with this email: %s", share.getEmail());
        return new Status(Operation.SHARE, false, status);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/changeAccess")
    public Status changeAccess(Share share, @Context HttpServletRequest request) {
        User user = getUser(share.getEmail());
        String status;
        if (user != null) {
            if(permissionsController.changeAccess(share.getToken().getId(),user.getId(), share.isReadOnly())) {
                LOG.info("Share - change access method is success(email: {}, id: {}",share.getEmail(), share.getToken().getId());
                status = String.format("Access changed to this email: %s", share.getEmail());
                return new Status(Operation.SHARE, true, status);
            }
            LOG.error("Share - change access method is failed(email: {}, id: {}",share.getEmail(), share.getToken().getId());
            status = String.format("Access changed failed to this email: %s", share.getEmail());
            return new Status(Operation.SHARE, false, status);
        }
        LOG.debug("Share - change access method is failed because no user found(email: {}, id: {}",share.getEmail(), share.getToken().getId());
        status = String.format("User not found with this email: %s", share.getEmail());
        return new Status(Operation.SHARE, false, status);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedWithMe")
    public List<UserFile> sharedWithMe(Token token) {
        int userId = userController.getUser("token", token.getToken()).getId();
        LOG.info("sharedWithMe method called with userId: {}", userId);
        return permissionsController.sharedFiles("Permissions.userId", userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedFiles")
    public List<UserFile> sharedFiles(Token token) {
        int userId = userController.getUser("token", token.getToken()).getId();
        LOG.info("sharedFiles method called with userId: {}", userId);
        return permissionsController.sharedFiles("Files.ownerId", userId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sharedWith")
    public List<Share> sharedWith(Token token) {
        int userId = userController.getUser("token", token.getToken()).getId();
        LOG.info("sharedWith method called with userId: {}", userId);
        return permissionsController.sharedWith(token.getId());
    }
}
