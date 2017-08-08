package util;

import controller.PermissionsController;
import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;

/**
 * Created by David Szilagyi on 2017. 08. 04..
 */
public abstract class ControllersFactory {
        protected UserFileController userFileController = new UserFileController(ConnectionUtil.DatabaseName.CoolDrive);
        protected UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);
        protected TransactionsController transactionsController = new TransactionsController(ConnectionUtil.DatabaseName.CoolDrive);
        protected PermissionsController permissionsController = new PermissionsController(ConnectionUtil.DatabaseName.CoolDrive);
}
