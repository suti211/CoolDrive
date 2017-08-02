package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.TransactionsController;
import dto.Token;
import dto.Transaction;
import util.ConnectionUtil;

@Path("/checkout")
public class CheckoutService {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction checkoutTransaction(Token token){
		Transaction requestedTransaction;
		TransactionsController transactionsController = new TransactionsController(ConnectionUtil.DatabaseName.CoolDrive);
		
		requestedTransaction = transactionsController.getTransaction(token.getId());
		
		System.out.println(requestedTransaction);
		
		return requestedTransaction;
	}
}
