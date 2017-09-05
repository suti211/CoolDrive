package service;

import dao.SimpleUserDao;
import dto.Operation;
import dto.Status;
import dto.Token;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ControllersFactory;
@Service
public class SimpleLogoutService implements LogoutService {
	
	private ControllersFactory controllersFactory;
	@Autowired
	public SimpleLogoutService(ControllersFactory controllersFactory) {
		this.controllersFactory = controllersFactory;
	}

	@Override
	public Status deleteUserToken(Token token) {
		try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
			User user = simpleUserDao.getUser("token", token.getToken());

			boolean tokenRemoved = simpleUserDao.deleteToken(user.getUserName());

			if (tokenRemoved) {
				return new Status(Operation.LOGOUT, true, "User logged out, token removed!");
			} else {
				return new Status(Operation.LOGOUT, false, "Failed to remove token!");
			}

		}
	}
}
