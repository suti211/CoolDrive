package service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;
import dto.StorageInfo;
import dto.Transaction;
import dto.User;
import dto.UserFile;
import util.ControllersFactory;

/**
 * Created by mudzso on 2017.08.07..
 */
@Path("/adminpage")
public class AdminService extends ControllersFactory {

	@GET
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> getAllTransaction() {
		try (TransactionsController transactionsController = getTransactionsController()) {
			return transactionsController.getAllTransaction();
		}
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {
		try (UserController userController = getUserController();
			 UserFileController userFileController = getUserFileController()) {
			List<User> users = userController.getAllUser();

			UserFile uf;

			for (User user : users) {
				uf = userFileController.getUserFile(user.getUserHomeId());
				user.setUserStorage(new StorageInfo(uf.getSize(), uf.getMaxSize()));
			}

			return users;
		}
	}

}
