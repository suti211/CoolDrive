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
public class SimpleTokenValidationService implements TokenValidationService {

	private ControllersFactory controllersFactory;
	@Autowired
	public SimpleTokenValidationService(ControllersFactory controllersFactory) {
		this.controllersFactory = controllersFactory;
	}

	@Override
	public Status validateToken(Token token) {
		try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
			User user = simpleUserDao.getUser("token", token.getToken());

			if (user != null) {

				return new Status(Operation.TOKENVALIDATION, true, "Token is valid!");
			} else {
				return new Status(Operation.TOKENVALIDATION, false, "Token is invalid!");
			}
		}
	}

	@Override
	public Status isAdmin(Token token) {
		try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
			User user = simpleUserDao.getUser("token", token.getToken());

			if (user != null) {
				if (user.isAdmin())return new Status(Operation.ADMINAUTH, true, "User is admin");
				return new Status(Operation.TOKENVALIDATION, true, "Token is valid!");
			} else {
				return new Status(Operation.TOKENVALIDATION, false, "Token is invalid!");
			}
		}
	}
}
