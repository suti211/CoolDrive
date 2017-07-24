package dao;

import dto.Transaction;

import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 24..
 */
public interface TransactionsDao {

    Transaction getTransaction (int userId);

    List<Transaction> getAllTransaction(int userId);

    List<Transaction> getAllTransaction();

    boolean addTransaction(Transaction transaction);
}
