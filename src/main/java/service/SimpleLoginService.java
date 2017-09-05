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
@Service
public class SimpleLoginService implements LoginService {
    private ControllersFactory controllersFactory;
    @Autowired
    public SimpleLoginService(ControllersFactory controllersFactory) {
        this.controllersFactory = controllersFactory;
    }


    private final Logger LOG = LoggerFactory.getLogger(SimpleLoginService.class);
    @Override
    public Status authenticateUser(User input){
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            int userId = simpleUserDao.checkUser(input.getUserName(), input.getPass());

            if (userId != -1) {
                User user = simpleUserDao.getUser(userId);

                if (user.isValidated()) {
                    simpleUserDao.setToken(input.getUserName());
                    user = simpleUserDao.getUser(userId);
                    return new Status(Operation.LOGIN, true, user.getUserName() + " " + user.getToken());
                } else {
                    return new Status(Operation.LOGIN, false, "User is not validated yet!");
                }
            } else {
                return new Status(Operation.LOGIN, false, "Username or password is SHIT!");
            }
        }
    }
}
