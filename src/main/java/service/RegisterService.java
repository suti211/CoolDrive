package service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import controller.UserController;
import controller.UserFileController;
import dto.Operation;
import dto.Status;
import dto.User;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.*;

@Path("/register")
public class RegisterService extends ControllersFactory {
	private final Logger LOG = LoggerFactory.getLogger(RegisterService.class);
	private final UserFileManager userFileManager = new UserFileManager();
	private final EmailSenderUtil emailSenderUtil = new EmailSenderUtil();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Status getUser(User input,@Context HttpServletRequest request) {
		try (UserFileController userFileController = getUserFileController();
			 UserController userController = getUserController()) {
			LOG.info("RegisterService post method is called with username: {}, from: {}", input.getUserName(), request.getRemoteAddr());

			int userID = userController.checkUser(input.getUserName(), input.getPass());

			if (userID == -1) {
				int startQuantity = 50;
				int parentId = 1;
				int userId = userController.registerUser(input);
				int userHomeId = userFileController.addNewUserFile(new UserFile(PathUtil.ROOT_PATH, 0, input.getUserName(), "dir", startQuantity, true, userId, parentId));
				if (userController.setHomeId(userId, userHomeId)) {
					userFileManager.saveFolder(input.getUserName());
					userController.setToken(input.getUserName());
					emailSenderUtil.sendEmail(input, userController.getUser("email", input.getEmail()).getToken());
					return new Status(Operation.REGISTER, true, "User successfully registered!");
				} else {
					return new Status(Operation.REGISTER, false, "Failed to add user to DB!");
				}
			} else {
				return new Status(Operation.REGISTER, false, "User already Exists!");
			}
		}
	}
}
