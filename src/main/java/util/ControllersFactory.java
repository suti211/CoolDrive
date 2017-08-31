package util;

import controller.PermissionsController;
import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;
import dao.PermissionsDao;
import dao.TransactionsDao;
import dao.UserDao;
import dao.UserFileDao;

public interface ControllersFactory {

    UserFileDao getUserFileController();

    UserDao getUserController();

    TransactionsDao getTransactionsController();

    PermissionsDao getPermissionsController();
}
