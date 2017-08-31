package service;

import dto.Transaction;
import dto.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface AdminService {

    List<Transaction> getAllTransaction();
    List<User> getAllUser();
}
