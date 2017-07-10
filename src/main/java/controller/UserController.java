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
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("DELETE FROM Users WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modifyUser(int id, String[] change) {
    }

    public void quantityChange(int id, double quantity) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("UPDATE Users SET quantity = ? WHERE id = ?");
            ps.setDouble(1, quantity);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int checkUser(String userName, String pass) {
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("SELECT * FROM Users WHERE username = ? AND pass = ?");
            ps.setString(1, userName);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next() != false) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
