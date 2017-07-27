package dao;

import dto.UserFile;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserFileDao {
    UserFile getUserFile(int id);

    int addNewUserFile(UserFile userFile);

    boolean modifyUserFile(UserFile userFile);

    boolean changeFolderCurrSize(int id, double size);

    List<UserFile> getAllFilesFromFolder(int id);

    boolean deleteUserFile(int id);

    int checkUserFile(UserFile userFile);

    boolean checkAvailableSpace(int id, double fileSize);
}
