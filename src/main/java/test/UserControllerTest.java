package test;

import controller.DatabaseController;
import controller.UserController;
import dto.User;
import org.junit.*;
import util.ConnectionUtil;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by mudzso on 2017.08.02..
 */
public class UserControllerTest {
    private static TestDbManager testDbManager = new TestDbManager(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private static Calendar currenttime = Calendar.getInstance();
    private UserController userController  = new UserController(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private static final double DELTA = 1e-15;
    private static User user = new User(
            0,
            "jani",
            "pass",
            "jani@gmail.com",
            false,
            "kiss",
            "jános",
            false,
            "asd",
            new Date((currenttime.getTime()).getTime()),
            1
    );
    @Test
    public void getUserAndRegisterUser() throws Exception {
        userController.registerUser(user);
        User result1 = userController.getUser(0);
        User result2 = userController.getUser("token","asd");
        User result3 = userController.getUser("email","jani@gmail.com");
        assertEquals(user.getPass(),result2.getPass());
        assertEquals(user.getToken(),result1.getToken());
        assertEquals(user.getFirstName(),result3.getFirstName());
    }




    @Test
    public void deleteUser() throws Exception {
        userController.changeValidation(0,true);
        userController.deleteUser(0);
        assertEquals(false,userController.getUser(0).isValidated());
    }

    @Test
    public void modifyUser() throws Exception {
        user.setFirstName("changed");
        userController.modifyUser(0,user);
        User result = userController.getUser(0);
        assertEquals("changed",result.getFirstName());
    }

    /*@Test
    public void setHomeId() throws Exception {
        userController.setHomeId(0,10);
        assertEquals(10,userController.getUser(0).getUserHomeId());
    } */

    @Test
    public void changeValidation() throws Exception {
        if (userController.changeValidation(0,true))
        assertEquals(true,userController.getUser(0).isValidated());
    }

    @Test
    public void checkUser() throws Exception {
        int result1 = userController.checkUser("jani","pass");
        int result2 = userController.checkUser("wrongInput","wrongInput");
        assertEquals(0,result1);
        assertEquals(-1,result2);

    }

    @Test
    public void setToken() throws Exception {
        userController.setToken("jani");
        assertNotEquals("asd",userController.getUser(0).getToken());
    }

    @Test
    public void deleteToken() throws Exception {
        userController.deleteToken("jani");
        assertEquals(null,userController.getUser(0).getToken());
    }

    @AfterClass
    public static void shutDown(){
        testDbManager.clearDataBase("Users");

    }

}