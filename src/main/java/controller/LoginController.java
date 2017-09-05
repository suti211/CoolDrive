package controller;

import dto.Status;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.LoginService;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.RequestWrapper;
@RequestMapping(
        value = "/login",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class LoginController {
    private LoginService loginService;
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }
    @RequestMapping(value = "", method= RequestMethod.POST)
    public Status authenticateUser(@RequestBody User input){
        return loginService.authenticateUser(input);
    }

}