package service;

import dto.Status;

public interface UserService {
    Status verifyUser(Object userToken);
}
