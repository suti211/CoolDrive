package util;

import controller.PermissionsController;
import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;

/**
 * Created by David Szilagyi on 2017. 08. 04..
 */
public abstract class ControllersFactory {
        protected UserFileController getUserFileController() {
                return new UserFileController(PropertiesHandler.DATABASENAME);
        }

        protected UserController getUserController() {
                return new UserController(ConnectionUtil.DatabaseName.CoolDrive);
        }

        protected TransactionsController getTransactionsController() {
                return new TransactionsController(ConnectionUtil.DatabaseName.CoolDrive);
        }

        protected PermissionsController getPermissionsController() {
                return new PermissionsController(ConnectionUtil.DatabaseName.CoolDrive);
        }
}
