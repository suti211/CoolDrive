package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.UserController;
import dto.Status;
import util.ConnectionUtil;

@Path("/login")
public class LoginService {

	UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status authenticateUser(User input) {
		
		
		
	}

}
