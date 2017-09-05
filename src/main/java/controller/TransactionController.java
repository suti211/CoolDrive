package controller;

import dto.Status;
import dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TransactionService;

import javax.ws.rs.core.MediaType;

@RequestMapping(
        value = "/transaction",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Status newTrans(@RequestBody Transaction transaction){
        return transactionService.createNewTransaction(transaction);
    }
}
