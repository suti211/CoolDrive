package service;

import dto.Status;
import dto.Transaction;

public interface TransactionService {
    Status createNewTransaction(Transaction transaction);
}
