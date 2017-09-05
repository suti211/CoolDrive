package service;

import dto.Token;
import dto.Transaction;

public interface CheckoutService {
    Transaction checkoutTransaction(Token token);
}
