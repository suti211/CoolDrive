package controller;

import dto.Status;
import dto.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.LogoutService;

import javax.ws.rs.core.MediaType;

@RequestMapping(
        value = "/logout",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class LogoutController {
    private LogoutService logoutService;
    @Autowired
    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @RequestMapping(value = "", method= RequestMethod.POST)
    public Status logOutUser(@RequestBody Token token){
        return logoutService.deleteUserToken(token);
    }
}
