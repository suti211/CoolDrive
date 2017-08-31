package util;

import controller.PermissionsController;
import controller.TransactionsController;
import controller.UserController;
import controller.UserFileController;
import dao.PermissionsDao;
import dao.TransactionsDao;
import dao.UserDao;
import dao.UserFileDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by David Szilagyi on 2017. 08. 04..
 */
public class SimpleControllersFactory implements ControllersFactory {

        private static SimpleControllersFactory instance = null;
        private UserDao userDao;
        private UserFileDao userFileDao;
        private TransactionsDao transactionsDao;
        private PermissionsDao permissionsDao;
        @Override
        public UserFileDao getUserFileController() {
                return userFileDao;
        }

        @Override
        public UserDao getUserController() {
                return userDao;
        }

        @Override
        public TransactionsDao getTransactionsController() {
                return transactionsDao;
        }

        @Override
        public PermissionsDao getPermissionsController() {
                return permissionsDao;
        }
        @Autowired
        private SimpleControllersFactory(UserDao userDao, UserFileDao userFileDao, TransactionsDao transactionsDao, PermissionsDao permissionsDao) {
                this.userDao = userDao;
                this.userFileDao = userFileDao;
                this.transactionsDao = transactionsDao;
                this.permissionsDao = permissionsDao;
        }


}
