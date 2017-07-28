package test;

import controller.UserController;

import dto.User;

import org.junit.runner.RunWith;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import util.ConnectionUtil;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

/**
 * Created by mudzso on 2017.07.27..
 */
@RunWith(MockitoJUnitRunner.class)
public class DbTest {
    private static final double DELTA = 1e-15;
    private Calendar currenttime = Calendar.getInstance();


    private UserController userController;
    @Mock
    private DataSource ds;
    @Mock
    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    User user;
    @Before
    public void setUp()throws Exception{
        assertNotNull(ds);
        //Mockito.when(con.createStatement()).thenReturn(mockStatment);
        Mockito.when(con.prepareStatement(any(String.class))).thenReturn(ps);
        Mockito.when(ds.getConnection()).thenReturn(con);
        //Mockito.when(con.createStatement().executeUpdate(any())).thenReturn(1);
        //Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
        user = new User(
                0,
                "jani",
                "pass",
                "jani@gmail.com",
                false,
                "kiss",
                "jános",
                false,
                0.0,
                0.0,
                "asd",
                new Date((currenttime.getTime()).getTime()),
                1
                );
            Mockito.when(rs.first()).thenReturn(true);
            Mockito.when(rs.getInt(1)).thenReturn(0);
            Mockito.when(rs.getString(2)).thenReturn(user.getUserName());
            Mockito.when(rs.getString(3)).thenReturn("pass");
            Mockito.when(rs.getString(4)).thenReturn("jani@gmail.com");
            Mockito.when(rs.getBoolean(5)).thenReturn(false);
            Mockito.when(rs.getString(6)).thenReturn("kiss");
            Mockito.when(rs.getString(7)).thenReturn("jános");
            Mockito.when(rs.getBoolean(8)).thenReturn(false);
            Mockito.when(rs.getDouble(9)).thenReturn(0.0);
            Mockito.when(rs.getDouble(10)).thenReturn(0.0);
            Mockito.when(rs.getString(11)).thenReturn("asd");
            Mockito.when(rs.getDate(12)).thenReturn(new Date(2016));
            Mockito.when(rs.getInt(13)).thenReturn(1);
            Mockito.when(ps.executeQuery()).thenReturn(rs);


    }


    @Test
    public void createAndRetrieveUser()throws Exception{
        userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive_Test);
        userController.registerUser(user);
        User result1 = userController.getUser(0);
        User result2 = userController.getUser("asd");
        assertEquals(user,result2);
        assertEquals(user,result1);
        userController.deleteUser(0);

    }

    @Test
    public void getUsage() throws Exception {
        double usage = userController.getUsage(0);
        assertEquals(0.0,usage,DELTA);

    }

    @Test
    public void deleteUser() throws Exception {
    }

    @Test
    public void modifyUser() throws Exception {
    }

    @Test
    public void quantityChange() throws Exception {
        userController.quantityChange(0,10.0);
        assertEquals(10.0,userController.getUser(0).getQuantity(),DELTA);
    }

    @Test
    public void changeValidation() throws Exception {
        userController.changeValidation(0,true);
        assertEquals(true,userController.getUser(0).isValidated());
    }

    @Test
    public void checkUser() throws Exception {
    }






}