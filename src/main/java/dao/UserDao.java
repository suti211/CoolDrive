package dao;

import dto.User;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserDao {
    User getUser(int id);

    double getUsage(int id);

    void registerUser(User user);

    void deleteUser(int id);

    void modifyUser(int id, String[] change);

    void quantityChange(double quantity);

    boolean loginCheck(String userName, String pass);

}
