package util;

import dao.SimplePermissionsDao;
import dao.SimpleTransactionsDao;
import dao.SimpleUserDao;
import dao.SimpleUserFileDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by David Szilagyi on 2017. 08. 04..
 */
public class SimpleControllersFactory implements ControllersFactory {

        private static SimpleControllersFactory instance = null;
        private SimpleUserDao userDao;
        private SimpleUserFileDao userFileDao;
        private SimpleTransactionsDao transactionsDao;
        private SimplePermissionsDao permissionsDao;
        @Override
        public SimpleUserFileDao getUserFileController() {
                return userFileDao;
        }

        @Override
        public SimpleUserDao getUserController() {
                return userDao;
        }

        @Override
        public SimpleTransactionsDao getTransactionsController() {
                return transactionsDao;
        }

        @Override
        public SimplePermissionsDao getPermissionsController() {
                return permissionsDao;
        }
        @Autowired

        public SimpleControllersFactory(SimpleUserDao userDao, SimpleUserFileDao userFileDao, SimpleTransactionsDao transactionsDao, SimplePermissionsDao permissionsDao) {
                this.userDao = userDao;
                this.userFileDao = userFileDao;
                this.transactionsDao = transactionsDao;
                this.permissionsDao = permissionsDao;
        }
}
