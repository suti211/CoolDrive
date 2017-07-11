package controller;

import dao.PermissionsDao;
import util.ConnectionUtil;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class PermissionsController extends DatabaseController implements PermissionsDao{

    public PermissionsController(ConnectionUtil.DatabaseName database) {
        super(database);
    }

    public boolean addFileToUser(int userId, int fileId) {
        return false;
    }

    public boolean removeFileFromUser(int userId, int fileId) {
        return false;
    }
}
