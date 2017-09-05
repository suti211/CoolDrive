package service;

import java.sql.Date;

import dao.SimpleTransactionsDao;
import dao.SimpleUserDao;
import dao.SimpleUserFileDao;
import dto.Operation;
import dto.Status;
import dto.Transaction;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ControllersFactory;
import util.EmailSenderUtil;

@Service
public class SimpleTransactionService implements TransactionService {
	private ControllersFactory controllersFactory;
	@Autowired
	public SimpleTransactionService(ControllersFactory controllersFactory) {
		this.controllersFactory = controllersFactory;
	}

	@Override
	public Status createNewTransaction(Transaction transaction) {
		try (SimpleUserDao simpleUserDao = controllersFactory.getUserController();
			 SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController();
			 SimpleTransactionsDao simpleTransactionsDao = controllersFactory.getTransactionsController()) {
			User user = simpleUserDao.getUser("token", transaction.getUserToken());

			if (user == null) {
				return new Status(Operation.NEWTRANSACTION, false, "Token mismatch, no such user!");
			}

			//date misery...
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date sqlDate = new Date(utilDate.getTime());

			Transaction newTransaction = new Transaction(user.getId(), transaction.getFirstName(), transaction.getLastName(), transaction.getZip(), transaction.getCity(), transaction.getAddress1(), transaction.getAddress2(), transaction.getPhone(), transaction.getBought(), sqlDate.toString());

			int transactionID = simpleTransactionsDao.addTransaction(newTransaction);

			System.out.println(transaction.toString());

			if (transactionID != -1) {
				simpleUserFileDao.increaseFileSize(user.getUserHomeId(), Double.parseDouble(newTransaction.getBought()));
				EmailSenderUtil sender = new EmailSenderUtil();
				sender.sendEmail(user, newTransaction, Operation.NEWTRANSACTION);
				return new Status(Operation.NEWTRANSACTION, true, "Transaction added succesfully!");
			} else {
				return new Status(Operation.NEWTRANSACTION, false, "Transaction wasn't added to database.");
			}

		}
	}
}
