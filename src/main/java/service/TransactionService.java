package service;

import java.sql.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;
import dto.Operation;
import dto.Status;
import dto.Transaction;
import dto.User;
import util.ConnectionUtil;
import util.ConnectionUtil.DatabaseName;

@Path("/transaction")
public class TransactionService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status createNewTransaction(Transaction	transaction){
		
		final DatabaseName DATABASE_NAME = ConnectionUtil.DatabaseName.CoolDrive;
		UserController userController = new UserController(DATABASE_NAME);
		UserFileController uFileController = new UserFileController(DATABASE_NAME);
		TransactionsController transactionsController = new TransactionsController(DATABASE_NAME);
		
		
		User user = userController.getUser("token", transaction.getUserToken());
		
		if(user == null){
			return new Status(Operation.NEWTRANSACTION, false, "Token mismatch, no such user!");
		}
		
		//date misery...
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date sqlDate = new Date(utilDate.getTime());
		
		Transaction newTransaction = new Transaction(user.getId(), transaction.getFirstName(), transaction.getLastName(), transaction.getZip(), transaction.getCity(), transaction.getAddress1(), transaction.getAddress2(), transaction.getPhone(), transaction.getBought(), sqlDate.toString());
		
		int transactionID = transactionsController.addTransaction(newTransaction);
		
		System.out.println(transaction.toString());
		
		if(transactionID != -1){
			uFileController.increaseFileSize(user.getUserHomeId(), Double.parseDouble(newTransaction.getBought()));
			return new Status(Operation.NEWTRANSACTION, true, "Transaction added succesfully!");
		} else {
			return new Status(Operation.NEWTRANSACTION, false, "Transaction wasn't added to database.");
		}
		
	}

}
