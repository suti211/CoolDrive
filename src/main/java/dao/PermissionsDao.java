package dao;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public interface PermissionsDao {

    boolean addFileToUser(int fileId, int userId, boolean readOnly);

    boolean removeFileFromUser(int fileId, int userId);

    boolean changeAccess(int fileId, int userId, boolean readOnly);

    boolean checkAccess(int fileId, int userId);
}
