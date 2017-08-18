package dao;

import dto.UserFile;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 07..
 */
public interface UserFileDao {
    UserFile getUserFile(int id);

    int getPublicUserFile(String publicLink);

    int addNewUserFile(UserFile userFile);

    boolean modifyUserFile(UserFile userFile);

    boolean changeFolderCurrSize(int id, double size);

    List<UserFile> getAllFilesFromFolder(int id);

    boolean deleteUserFile(int id);

    int checkUserFile(String filename, String extension, int parentId);

    boolean checkAvailableSpace(int id, double fileSize);

    boolean setFileSize(int id, double fileSize);

    boolean increaseFileSize(int homeId, double increment);

    boolean setPublicLink(int fileId, int userId);

    boolean deletePublicLink(String publicLink, int fileId, int userId);

    String getPublicLink(int fileId, int userId);
}
