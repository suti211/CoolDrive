package service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import controller.UserController;
import dto.Operation;
import dto.Status;
import dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ControllersFactory;

@Path("/login")
public class LoginService extends ControllersFactory {
    private final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("")
    public Status authenticateUser(User input, @Context HttpServletRequest request) {
        LOG.info("LoginService Post method is called with username: {} from: {}", input.getUserName(), request.getRemoteAddr());
        try (UserController userController = getUserController()) {
            int userId = userController.checkUser(input.getUserName(), input.getPass());

            if (userId != -1) {
                User user = userController.getUser(userId);

                if (user.isValidated()) {
                    userController.setToken(input.getUserName());
                    user = userController.getUser(userId);
                    return new Status(Operation.LOGIN, true, user.getUserName() + " " + user.getToken());
                } else {
                    return new Status(Operation.LOGIN, false, "User is not validated yet!");
                }
            } else {
                return new Status(Operation.LOGIN, false, "Username or password is SHIT!");
            }
        }
    }
}
