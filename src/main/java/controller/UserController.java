package controller;

import dao.UserDao;
import dto.User;
import util.ConnectionUtil;

import java.sql.*;

/**
 * Created by David Szilagyi on 2017. 07. 10..
 */
public class UserController implements UserDao {

    private Connection con = null;

    public UserController(ConnectionUtil.DatabaseName database) {
        this.con = ConnectionUtil.getConnection(database);
    }

    public User getUser(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Users WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return new User(rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("pass"),
                    rs.getString("email"),
                    rs.getBoolean("validated"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getBoolean("admin"),
                    rs.getDouble("quantity"),
                    rs.getDouble("usage"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getUsage(int id) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Users WHERE id = ?");
            ps.setInt(1, id);
            return ps.executeQuery().getDouble("usage");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void registerUser(User user) {
    }

    public void deleteUser(int id) {
    }

    public void modifyUser(int id, String[] change) {
    }

    public void quantityChange(int id, double quantity) {
    }

    public boolean loginCheck(String userName, String pass) {
        return false;
    }
}
