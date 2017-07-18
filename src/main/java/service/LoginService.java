package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import util.ConnectionUtil;
import util.TokenGenerator;

@Path("/login")
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status authenticateUser(User input, @Context HttpServletRequest request) {
		LOG.info("LoginService Post method is called with username: {} from: {}",input.getUserName(),request.getRemoteAddr());
		int userId = userController.checkUser(input.getUserName(), input.getPass());
		
		if(userId != -1){
			User user = userController.getUser(userId);
			HttpSession session = request.getSession(true);
			
			if(user.isValidated()){			
				return new Status(Operation.LOGIN, true, user.getUserName() + " " + user.getToken());
			} else {
				return new Status(Operation.LOGIN, false, "User is not validated yet!");
			}
		} else {
			return new Status(Operation.LOGIN, false, "Username or password is SHIT!");
		}
	}
	
	@GET
	public String test() {
		return "<h1>aha</h1>";
	}

}
