package controller;

import util.ConnectionUtil;
import java.sql.Connection;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class DatabaseController {

    protected Connection con = null;

    public DatabaseController(ConnectionUtil.DatabaseName database) {
        this.con = ConnectionUtil.getConnection(database);
    }

    public Connection getCon() {
        return con;
    }
}