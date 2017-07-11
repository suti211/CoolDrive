package dao;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public interface PermissionsDao {

    boolean addFileToUser(int userId, int fileId);

    boolean removeFileFromUser(int userId, int fileId);
}
