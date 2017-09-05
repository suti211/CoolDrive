package service;

import dto.Status;
import dto.User;

public interface RegisterService {
    Status getUser(User input);
}
