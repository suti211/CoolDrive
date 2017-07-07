package dao;

import object.User;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserDao {
    User getUser();
    double getUsage();
    void registerUser(User user);
    void deleteUser(int id);
    void modifyUser(int id, String[] change);
    void quantityChange(double quantity);
    void loginCheck(String userName, String pass);

}
