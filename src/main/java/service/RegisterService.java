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
import util.ConnectionUtil;

@Path("/register")
public class RegisterService {
	private static final Logger LOG = LoggerFactory.getLogger(RegisterService.class);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status getUser(User input,@Context HttpServletRequest request) {
		LOG.info("RegisterService post method is called with username: {}, from: {}",input.getUserName(),request.getRemoteAddr());
		
		UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);
		
		int userID = userController.checkUser(input.getUserName(), input.getPass());
		
		if(userID == -1){
			if(userController.registerUser(input)){
				return new Status(Operation.REGISTER, true, "User successfully registered!");
			} else {
				return new Status(Operation.REGISTER, false, "Failed to add user to DB!");
			}
		} else {
			return new Status(Operation.REGISTER, false, "User already Exists!");
		}
		
	}

//	@GET
//	public String test() {
//		return "<h1>aha</h1>";
//	}

}
