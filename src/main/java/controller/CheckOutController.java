package controller;

import dto.Status;
import dto.Token;
import dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.CheckoutService;

import javax.ws.rs.core.MediaType;

@RequestMapping(
        value = "/logout",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class CheckOutController {
    private CheckoutService checkoutService;
    @Autowired
    public CheckOutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @RequestMapping(value = "",method = RequestMethod.POST)
    public Transaction checkoutTransaction(@RequestBody Token token){
        return checkoutService.checkoutTransaction(token);
    }
}
