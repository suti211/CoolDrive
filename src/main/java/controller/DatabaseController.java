package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class DatabaseController {


    private static final Logger LOG = LoggerFactory.getLogger(DatabaseController.class);
    protected Connection con = null;

    public DatabaseController(DataSource ds) {
        try {
            this.con = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DatabaseController(ConnectionUtil.DatabaseName database) {
        this.con = ConnectionUtil.getConnection(database);
        LOG.info("Connected to {}",database);
    }



    public Connection getCon() {
        return con;
    }

}