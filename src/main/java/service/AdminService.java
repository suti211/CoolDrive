package service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dto.StorageInfo;
import dto.Transaction;
import dto.User;
import dto.UserFile;
import util.ControllersUtil;

/**
 * Created by mudzso on 2017.08.07..
 */
@Path("/adminpage")
public class AdminService extends ControllersUtil {

	@GET
	@Path("/transactions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Transaction> getAllTransaction() {
		return transactionsController.getAllTransaction();
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {
		List<User> users = userController.getAllUser();

		UserFile uf;

		for (User user : users) {
			uf = userFileController.getUserFile(user.getUserHomeId());
			user.setUserStorage(new StorageInfo(uf.getSize(), uf.getMaxSize()));
		}

		return users;
	}

}
