package controller;

import dao.TransactionsDao;
import dto.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 24..
 */
public class TransactionsController extends DatabaseController implements TransactionsDao {

    private static final Logger LOG = LoggerFactory.getLogger(PermissionsController.class);

    public TransactionsController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public Transaction getTransaction(int userId) {
        return null;
    }

        public List<Transaction> getAllTransaction(int userId) {
        return null;
    }

    public List<Transaction> getAllTransaction() {
        return null;
    }

    public boolean addTransaction(Transaction transaction) {
        return false;
    }
}
