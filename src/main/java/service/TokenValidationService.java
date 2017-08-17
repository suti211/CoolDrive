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
import util.ControllersFactory;


@Path("/token")
public class TokenValidationService extends ControllersFactory {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Status validateToken(Token token) {
		try (UserController userController = getUserController()) {
			User user = userController.getUser("token", token.getToken());

			if (user != null) {
				return new Status(Operation.TOKENVALIDATION, true, "Token is valid!");
			} else {
				return new Status(Operation.TOKENVALIDATION, false, "Token is invalid!");
			}
		}
	}
}
