package service;

import dto.Token;
import dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ControllersFactory;

@Service
public class SimpleCheckoutService implements CheckoutService {
	private ControllersFactory controllersFactory;
	@Autowired
	public SimpleCheckoutService(ControllersFactory controllersFactory) {
		this.controllersFactory = controllersFactory;
	}
	@Override
	public Transaction checkoutTransaction(Token token){
		Transaction requestedTransaction;

		requestedTransaction = controllersFactory.getTransactionsController().getTransaction(token.getId());
		
		System.out.println(requestedTransaction);
		
		return requestedTransaction;
	}
}
