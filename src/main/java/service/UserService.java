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
@Path("/user")
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/verify")
    public Status verifyUser(@Context HttpServletRequest request) {
        String email = request.getParameter("email");
        String token = request.getParameter("token");
        User user = userController.getToken(email);
        if (!user.isValidated()) {
            if (user.getToken().equalsIgnoreCase(token)) {
                userController.deleteToken(user.getUserName());
                userController.changeValidation(user.getId(), true);
                return new Status(Operation.VERIFICATION, true, "User is now validated!");
            } else {
                return new Status(Operation.VERIFICATION, false, "Invalid token or email!");
            }
        } else {
            return new Status(Operation.VERIFICATION, false, "User already validated!");
        }
    }
}
