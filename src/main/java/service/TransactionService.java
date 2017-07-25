package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.Operation;
import dto.Status;
import dto.Token;

@Path("/transaction")
public class TransactionService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status createNewTransaction(Token token){
		return new Status(Operation.NEWTRANSACTION, true, "ezmostatransactionID");
	}

}
