package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import java.sql.Connection;

/**
 * Created by David Szilagyi on 2017. 07. 11..
 */
public class DatabaseController {
  
    private final Logger LOG = LoggerFactory.getLogger(DatabaseController.class);
    protected Connection con = null;

    public DatabaseController(String database) {
        this.con = ConnectionUtil.getConnection(database);
        LOG.info("Connected to {}",database);
    }

    public Connection getCon() {
        return con;
    }
}