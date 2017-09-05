package service;

import dao.SimpleUserDao;
import dto.Operation;
import dto.Status;
import dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.ControllersFactory;

/**
 * Created by David Szilagyi on 2017. 08. 01..
 */
@Service
public class SimpleUserService implements UserService {
    private ControllersFactory controllersFactory;
    @Autowired
    public SimpleUserService(ControllersFactory controllersFactory) {
        this.controllersFactory = controllersFactory;
    }

    private final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class);

    @Override
    public Status verifyUser(Object userToken) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            String token = String.valueOf(userToken);
            User user = simpleUserDao.getUser("token", token);
            if (user != null) {
                LOG.info("user found with this token: {}, email: {}", token, user.getEmail());
                if (!user.isValidated()) {
                    simpleUserDao.deleteToken(user.getUserName());
                    simpleUserDao.changeValidation(user.getId(), true);
                    return new Status(Operation.VERIFICATION, true, user.getEmail() + " is now validated!");
                } else {
                    LOG.info("user is already validated with this email: {}", user.getEmail());
                    return new Status(Operation.VERIFICATION, false, user.getEmail() + " is already validated!");
                }
            }
            LOG.error("user is null in verifyUser with this token: {}", token);
            return new Status(Operation.VERIFICATION, false, "Invalid token!");
        }
    }
}
