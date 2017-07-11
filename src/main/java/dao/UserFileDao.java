package dao;

import dto.UserFile;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserFileDao {
    UserFile getUserFile();

    void addNewUserFile(UserFile userFile);

    void modifyUserFile(int id, String[] change);

    void deleteUserFile(int id);
}
