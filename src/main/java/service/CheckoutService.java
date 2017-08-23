package service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import dto.Token;
import dto.Transaction;
import util.ControllersFactory;

@Path("/checkout")
public class CheckoutService extends ControllersFactory {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Transaction checkoutTransaction(Token token){
		Transaction requestedTransaction;

		requestedTransaction = getTransactionsController().getTransaction(token.getId());
		
		System.out.println(requestedTransaction);
		
		return requestedTransaction;
	}
}
