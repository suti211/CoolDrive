package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import dto.User;

@Path("/register")
public class RegisterService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public User getUser(final User input){
		System.out.println(input.toString());
		return input;
	}
	
	@GET
	public String test(){
		return "<h1>aha</h1>";
	}
	
}
