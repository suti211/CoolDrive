package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import java.sql.Connection;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class DatabaseController implements AutoCloseable {
  
    private final Logger LOG = LoggerFactory.getLogger(DatabaseController.class);
    protected Connection con = null;
    protected String DatabaseName;

    public DatabaseController(ConnectionUtil.DatabaseName database) {
        this.con = ConnectionUtil.getConnection(database);
        LOG.info("Connected to {}",database);
    }

    public Connection getCon() {
        return con;
    }

    @Override
    public void close() {
        try {
            con.close();
        } catch (Exception e) {
            LOG.error("Connection cannot be closed",e);
        }
    }
}