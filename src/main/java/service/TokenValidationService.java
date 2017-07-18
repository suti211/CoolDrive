package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.Operation;
import dto.Status;
import dto.Token;


@Path("/token")
public class TokenValidationService {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Status validateToken(Token token){
		
		return new Status(Operation.TOKENVALIDATION, true, "Token is valid!");
	}
}
