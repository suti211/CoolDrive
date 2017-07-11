package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import controller.UserController;
import dto.Operation;
import dto.Status;
import dto.User;
import util.ConnectionUtil;

@Path("/login")
public class LoginService {

	UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status authenticateUser(User input, @Context HttpServletRequest request) {
		
		int userId = userController.checkUser(input.getUserName(), input.getPass());
		
		if(userId != -1){
			User user = userController.getUser(userId);
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			return new Status(Operation.LOGIN, true, user.getUserName() + " logged in succesfully!");
		} else {
			return new Status(Operation.LOGIN, false, "Username or password is SHIT!");
		}
	}
	
	@GET
	public String test() {
		return "<h1>aha</h1>";
	}
}
