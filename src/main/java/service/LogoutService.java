package service;

import dto.Status;
import dto.Token;

public interface LogoutService {
    Status deleteUserToken(Token token);
}
