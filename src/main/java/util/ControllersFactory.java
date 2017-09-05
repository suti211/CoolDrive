package util;

import dao.SimplePermissionsDao;
import dao.SimpleTransactionsDao;
import dao.SimpleUserDao;
import dao.SimpleUserFileDao;

public interface ControllersFactory {

    SimpleUserFileDao getUserFileController();

    SimpleUserDao getUserController();

    SimpleTransactionsDao getTransactionsController();

    SimplePermissionsDao getPermissionsController();
}
