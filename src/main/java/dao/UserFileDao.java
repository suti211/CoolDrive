package dao;

import dto.UserFile;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserFileDao {
    UserFile getUserFile(int id);

    boolean addNewUserFile(UserFile userFile);

    boolean modifyUserFile(int id, UserFile userFile);

    boolean changeFolderSize(int id, double maxSize);

    boolean deleteUserFile(int id);

    boolean checkUserFile(UserFile userFile);
}
