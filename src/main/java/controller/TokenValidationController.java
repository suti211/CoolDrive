package controller;

import dto.Status;
import dto.Token;
import dto.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.TokenValidationService;

import javax.ws.rs.core.MediaType;

@RequestMapping(
        value = "/token",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class TokenValidationController {
    private TokenValidationService tokenValidationService;
    @Autowired
    public TokenValidationController(TokenValidationService tokenValidationService) {
        this.tokenValidationService = tokenValidationService;
    }
    @RequestMapping(value = "/tokentest",method = RequestMethod.POST)
    public Status isValid(@RequestBody Token token){
        return tokenValidationService.validateToken(token);
    }

    @RequestMapping(value = "/admintest",method = RequestMethod.POST)
    public Status isAdmin(@RequestBody Token token){
        return tokenValidationService.isAdmin(token);
    }
}
