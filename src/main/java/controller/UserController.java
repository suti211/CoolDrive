package controller;

import dao.UserDao;
import dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import util.TokenGenerator;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 10..
 */
public class UserController extends DatabaseController implements UserDao {
    private final Logger LOG = LoggerFactory.getLogger(UserController.class);

    public UserController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public User getUser(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOG.info("Find user with this id: {} User(username: {}, email: {})", id, rs.getString("username"), rs.getString("email"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("email"),
                        rs.getBoolean("validated"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getBoolean("admin"),
                        rs.getString("token"),
                        rs.getDate("registerdate"),
                        rs.getInt("userhomeid")
                );
            }
        } catch (SQLException e) {
            LOG.error("getUser(id) if failed with Exception", e);
        }
        LOG.debug("User not found with this id: {} in getUser(id) method", id);
        return null;
    }
    
    public List<User> getAllUser(){
    	String sql = String.format("SELECT * FROM Users");
    	List<User> users = new ArrayList<>();
    	PreparedStatement ps = null;
    	
    	try{
    		LOG.info("getAllUser() method invoked!");
    		ps = con.prepareStatement(sql);
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()){
    			users.add(new User(
    					rs.getInt("id"),
    					rs.getString("userName"),
    					rs.getString("email"),
    					rs.getBoolean("validated"),
    					rs.getString("firstName"),
    					rs.getString("lastName"),
    					rs.getBoolean("admin"),
    					rs.getString("token"),
    					rs.getDate("registerDate"),
    					rs.getInt("userHomeId")
    				));
    		}
    		return users;
    	} catch (SQLException e) {
    		LOG.error("getAllUser() failed with Exception {}", e);
		}
    	LOG.debug("getAllUsers() failed (null)!");
    	return null;
    }

    public User getUser(String columnName, String value) {
        String sql = String.format("SELECT * FROM Users WHERE %s = ?", columnName);
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOG.info("Find user with columnName: {} - value: {}, User(username: {}, email: {})",columnName, value, rs.getString("username"), rs.getString("email"));
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("pass"),
                        rs.getString("email"),
                        rs.getBoolean("validated"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getBoolean("admin"),
                        rs.getString("token"),
                        rs.getDate("registerdate"),
                        rs.getInt("userhomeid")
                );
            }
        } catch (SQLException e) {
            LOG.error("getUser(columnName: {}, value: {}) if failed with Exception",columnName, value, e);
        }
        LOG.debug("User not found - columnName: {}, value: {} in getUser method", columnName, value);
        return null;
    }

    public int registerUser(User user) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "INSERT INTO Users(username, pass, email, validated, firstname, lastname, admin, token, registerdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPass());
            ps.setString(3, user.getEmail());
            ps.setBoolean(4, false);
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getLastName());
            ps.setBoolean(7, false);
            ps.setString(8, user.getToken());
            int success = ps.executeUpdate();
            if (success > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                LOG.info("User(username: {}, email: {) successfully registered", user.getUserName(), user.getEmail());
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOG.error("register user is failed with Exception", e);
        }
        return -1;
    }

    public boolean deleteUser(int id) {
        LOG.info("User with this id: {} is successfully deleted", id);
        return changeValidation(id, false);
    }

    public boolean modifyUser(int id, User user) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Users SET pass = ?," + "email = ?," + "firstname = ?," + "lastname =? WHERE id = ?");
            ps.setString(1, user.getPass());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setInt(5, id);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("User(username: {}, email: {)with this id: {} successfully modified", user.getUserName(), user.getEmail(), id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Modify user is failed with Exception", e);
        }
        LOG.debug("User not found with this id: {} in modifyUser method", id);
        return false;
    }

    public boolean setHomeId(int id, int userHomeID) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(
                    "UPDATE Users SET userhomeid = ? WHERE id = ?");
            ps.setInt(1, userHomeID);
            ps.setInt(2, id);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("setHomeId {} successful to this id: {}", userHomeID, id);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("setHomeId is failed with Exception", e);
        }
        return false;
    }

    public boolean changeValidation(int id, boolean validate) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Users SET validated = ? WHERE id = ?");
            ps.setBoolean(1, validate);
            ps.setInt(2, id);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Validation is successfully changed with this id: {} validated: {}", id, validate);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Change validation is failed with Exception", e);
        }
        LOG.debug("User not found with this id: {} in changeValidation method", id);
        return false;
    }

    public int checkUser(String userName, String pass) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT id FROM Users WHERE username = ? AND pass = ?");
            ps.setString(1, userName);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LOG.info("User checking is succeeded(username: {})", userName);
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOG.error("User checking is failed with Exception", e);
        }
        LOG.debug("Wrong username: {} and/or pass in checkUser method", userName);
        return -1;
    }

    public boolean setToken(String userName) {
        String token = TokenGenerator.createToken();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Users SET token = ? WHERE username = ?");
            ps.setString(1, token);
            ps.setString(2, userName);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Token successfully added to user: {}", userName);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Token failed to added with Exception", e);
        }
        return false;
    }

    public boolean deleteToken(String userName) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Users SET token = NULL WHERE username = ?");
            ps.setString(1, userName);
            int success = ps.executeUpdate();
            if (success > 0) {
                LOG.info("Token successfully deleted from user: {}", userName);
                return true;
            }
        } catch (SQLException e) {
            LOG.error("Token failed to deleted with Exception", e);
        }
        return false;
    }
}
