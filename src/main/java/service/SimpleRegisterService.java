package service;

import dao.SimpleUserDao;
import dao.SimpleUserFileDao;
import dto.Operation;
import dto.Status;
import dto.User;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.*;

@Service
public class SimpleRegisterService implements RegisterService {
	private ControllersFactory controllersFactory;
	private PropertiesHandler propertiesHandler;
	private UserFileManager userFileManager;
	@Autowired
	public SimpleRegisterService(ControllersFactory controllersFactory, PropertiesHandler propertiesHandler, UserFileManager userFileManager) {
		this.controllersFactory = controllersFactory;
		this.propertiesHandler = propertiesHandler;
		this.userFileManager = userFileManager;
	}

	private final Logger LOG = LoggerFactory.getLogger(SimpleRegisterService.class);
	private final EmailSenderUtil emailSenderUtil = new EmailSenderUtil();
	@Override
	public Status getUser(User input) {
		try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController();
			 SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
			int userID = simpleUserDao.checkUser(input.getUserName(), input.getPass());

			if (userID == -1) {
				int startQuantity = 50;
				int parentId = 1;
				int userId = simpleUserDao.registerUser(input);
				int userHomeId = simpleUserFileDao.addNewUserFile(new UserFile(propertiesHandler.getROOTPATH(), 0, input.getUserName(), "dir", startQuantity, true, userId, parentId, "Homefolder"));
				if (simpleUserDao.setHomeId(userId, userHomeId)) {
					userFileManager.saveFolder(input.getUserName());
					simpleUserDao.setToken(input.getUserName());
					emailSenderUtil.sendEmail(input, simpleUserDao.getUser("email", input.getEmail()).getToken(), Operation.REGISTER);
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
