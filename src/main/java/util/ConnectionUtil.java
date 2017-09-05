package util;

import java.sql.Connection;

public interface ConnectionUtil {
    String getUrl(String databaseName);

    Connection getConnection();

    Connection getConnection(String databaseName);

    Connection getConnection(String databaseName, String user, String password);
}
