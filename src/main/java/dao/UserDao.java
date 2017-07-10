package dao;

import dto.User;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserDao {
    User getUser(int id);

    double getUsage(int id);

    boolean registerUser(User user);

    boolean deleteUser(int id);

    boolean modifyUser(int id, User user);

    boolean quantityChange(int id, double quantity);

    boolean changeValidation(int id, boolean validate);

    int checkUser(String userName, String pass);
}
