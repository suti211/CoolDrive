package service;

import controller.UserController;
import dto.Operation;
import dto.Status;
import dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
@Path("")
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/verify")
    public Status verifyUser(@Context HttpServletRequest request) {
        String token = request.getParameter("token");
        User user = userController.getUser("token", token);
        if(user != null) {
            LOG.info("user found with this token: {}, email: {}", token, user.getEmail());
            if (!user.isValidated()) {
                    userController.deleteToken(user.getUserName());
                    userController.changeValidation(user.getId(), true);
                    return new Status(Operation.VERIFICATION, true, user.getEmail() + " is now validated!");
            } else {
                LOG.info("user is already validated with this email: {}", user.getEmail());
                return new Status(Operation.VERIFICATION, false, user.getEmail() + " is already validated!");
            }
        }
        LOG.error("user if null in verifyUser with this token: {}", token);
        return new Status(Operation.VERIFICATION, false, "Invalid token!");
    }
}
