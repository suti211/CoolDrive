package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.Operation;
import dto.Status;
import dto.User;

@Path("/register")
public class RegisterService {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status getUser(User input) {
		System.out.println(input.toString());
		
		UserController userController = new UserController();
		
		int userID = userController.checkUser(input.getUserName(), input.getPass());
		
		if(userID == -1){
			return new Status(Operation.REGISTER, true, "User successfully registered!");
		} else {
			return new Status(Operation.REGISTER, false, "User already Exists!");
		}
		
	}

//	@GET
//	public String test() {
//		return "<h1>aha</h1>";
//	}

}
