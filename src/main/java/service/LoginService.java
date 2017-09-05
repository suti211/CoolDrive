package service;

import dto.Status;
import dto.User;

public interface LoginService {
    Status authenticateUser(User input);
}
