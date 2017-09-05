package controller;

import dto.Status;
import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.RegisterService;

import javax.ws.rs.core.MediaType;

@RequestMapping(
        value = "/register",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class RegisterController {
    private RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Status getUser(@RequestBody User user){
        return registerService.getUser(user);
    }
}
