package util;

import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by mudzso on 2017.07.05..
 */
public class ConnectionUtil {
    private PropertiesHandler propertiesHandler;
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String DATABASE_HOST = "192.168.150.86";
//    private static final String DATABASE_USER = "void";
//    private static final String DATABASE_PASSWORD = "void";
    @Autowired
    public ConnectionUtil(PropertiesHandler propertiesHandler) {
        this.propertiesHandler = propertiesHandler;
        try {
            Class.forName(DRIVER).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    public String getUrl(String databaseName) {
        return String.format(
                "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=%s&serverTimezone=%s&useSSL=true",
                propertiesHandler.getDATABASEHOST(),
                propertiesHandler.getDATABASEPORT(),
                databaseName == null ? "" : databaseName,
                "UTF-8",
                "Europe/Budapest");
    }


    public  Connection getConnection() {
        return getConnection(null);
    }

    public  Connection getConnection(String databaseName) {
        try {
            return DriverManager.getConnection(getUrl(databaseName), propertiesHandler.getDATABASEUSER(), propertiesHandler.getDATABASEPASSWORD());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public Connection getConnection(String databaseName,String user,String password) {
        try {
            return DriverManager.getConnection(getUrl(databaseName), user, password);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }



}
