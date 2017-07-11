package controller;

import dao.UserDao;
import dto.User;
import util.ConnectionUtil;
import java.sql.*;

/**
 * Created by David Szilagyi on 2017. 07. 10..
 */
public class UserController extends DatabaseController implements UserDao {

	public UserController(ConnectionUtil.DatabaseName database) {
	    super(database);
    }

	public User getUser(int id) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("SELECT * FROM Users WHERE id = ?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new User(rs.getInt("id"), rs.getString("username"), rs.getString("pass"), rs.getString("email"),
						rs.getBoolean("validated"), rs.getString("firstname"), rs.getString("lastname"),
						rs.getBoolean("admin"), rs.getDouble("quantity"), rs.getDouble("usage"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public double getUsage(int id) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("SELECT `usage` FROM Users WHERE id = ?");
			ps.setInt(1, id);
			return ps.executeQuery().getDouble("usage");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean registerUser(User user) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(
					"INSERT INTO Users(username, pass, email, validated, firstname, lastname, admin, quantity, `usage`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPass());
			ps.setString(3, user.getEmail());
			ps.setBoolean(4, false);
			ps.setString(5, user.getFirstName());
			ps.setString(6, user.getLastName());
			ps.setBoolean(7, false);
			ps.setDouble(8, 0);
			ps.setDouble(9, 0);
			int success = ps.executeUpdate();
			return success > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteUser(int id) {
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
			return success > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean quantityChange(int id, double quantity) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("UPDATE Users SET quantity = ? WHERE id = ?");
			ps.setDouble(1, quantity);
			ps.setInt(2, id);
			int success = ps.executeUpdate();
			return success > 0;
		} catch (SQLException e) {
			e.printStackTrace();
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
			return success > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
