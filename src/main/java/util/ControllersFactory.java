package util;

import controller.PermissionsController;
import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;

/**
 * Created by David Szilagyi on 2017. 08. 04..
 */
public abstract class ControllersFactory {
        protected UserFileController userFileController = new UserFileController(PropertiesHandler.DATABASENAME);
        protected UserController userController = new UserController(PropertiesHandler.DATABASENAME);
        protected TransactionsController transactionsController = new TransactionsController(PropertiesHandler.DATABASENAME);
        protected PermissionsController permissionsController = new PermissionsController(PropertiesHandler.DATABASENAME);
}
