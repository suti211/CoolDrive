package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import controller.UserController;
import dto.Operation;
import dto.Status;
import dto.Token;
import dto.User;
import util.SimpleControllersFactory;

@Path("/logout")
public class LogoutService extends SimpleControllersFactory {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("")
	public Status deleteUserToken(Token token) {
		try (UserController userController = getUserController()) {
			User user = userController.getUser("token", token.getToken());

			boolean tokenRemoved = userController.deleteToken(user.getUserName());

			if (tokenRemoved) {
				return new Status(Operation.LOGOUT, true, "User logged out, token removed!");
			} else {
				return new Status(Operation.LOGOUT, false, "Failed to remove token!");
			}

		}
	}
}
