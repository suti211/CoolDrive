package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PropertiesHandler {
    private String DATABASEHOST;
    private String DATABASEPASSWORD;
    private String DATABASEUSER;
    private String DATABASENAME;
    private String DATABASEPORT;
    private String ROOTPATH;
    private Properties properties;
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);
    private  Set<String>propertieNames= new HashSet<>(Arrays.asList(
            "DATABASEHOST",
            "DATABASEPASSWORD",
            "DATABASEUSER",
            "DATABASENAME",
            "DATABASEPORT",
            "ROOTPATH"
    ));
    public PropertiesHandler() {
        if (getProperties()){
            init();
        }else {
            LOG.error("Properties not found");
        }
    }
    private boolean getProperties(){
        boolean result = true;
        properties = System.getProperties();
        if(!checkProperties(properties.stringPropertyNames())){
            Map<String,String> envs = System.getenv();
            if (checkProperties(envs.keySet())) {
                properties = new Properties();
                for (Map.Entry<String, String> entry : envs.entrySet()) {
                    if (propertieNames.contains(entry.getKey()))
                        properties.setProperty(entry.getKey(), entry.getValue());
                }

            }
            result = false;
        }
        return result;
    }
    private void init(){
        DATABASEPORT = properties.getProperty("DATABASEPORT");
        DATABASEHOST = properties.getProperty("DATABASEHOST");
        DATABASEPASSWORD = properties.getProperty("DATABASEPASSWORD");
        DATABASEUSER = properties.getProperty("DATABASEUSER");
        DATABASENAME = properties.getProperty("DATABASENAME");
        ROOTPATH = properties.getProperty("ROOTPATH");
    }
    public  boolean checkProperties(Set<String> properties){

        if (!properties.containsAll(propertieNames)){
            LOG.error("Wrong input");
            return false;
        }

        return true;

    }

    public String getDATABASEHOST() {
        return DATABASEHOST;
    }

    public String getDATABASEPASSWORD() {
        return DATABASEPASSWORD;
    }

    public String getDATABASEUSER() {
        return DATABASEUSER;
    }

    public String getDATABASENAME() {
        return DATABASENAME;
    }

    public String getDATABASEPORT() {
        return DATABASEPORT;
    }

    public String getROOTPATH() {
        return ROOTPATH;
    }
}

