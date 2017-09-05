package service;

import dto.Status;
import dto.Token;

public interface TokenValidationService {
    Status validateToken(Token token);

    Status isAdmin(Token token);
}
