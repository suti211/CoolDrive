package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class PropertiesHandler {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesHandler.class);
    private static Set<String>propertieNames= new HashSet<>(Arrays.asList(
            "DATABASEHOST",
            "DATABASEPASSWORD",
            "DATABASEUSER",
            "DATABASENAME",
            "DATABASEPORT"
    ));

    private static Properties properties;
    public PropertiesHandler() {
        properties = System.getProperties();
        if(!checkProperties(properties.stringPropertyNames())){
            Map<String,String> envs = System.getenv();
            if (checkProperties(envs.keySet())){
                properties = new Properties();
                for (Map.Entry<String,String> entry:envs.entrySet()) {
                    if (propertieNames.contains(entry.getKey()))
                    properties.setProperty(entry.getKey(),entry.getValue());
                }
            }
            System.exit(1);
        }
    }

    public static  String DATABASEHOST;
    public static  String DATABASEPASSWORD;
    public static  String DATABASEUSER;
    public static  String DATABASENAME;
    public static  String DATABASEPORT;
    public static InputStream in;
        static {
            DATABASEPORT = properties.getProperty("DATABASEPORT");
            DATABASEHOST = properties.getProperty("DATABASEHOST");
            DATABASEPASSWORD = properties.getProperty("DATABASEPASSWORD");
            DATABASEUSER = properties.getProperty("DATABASEUSER");
            DATABASENAME = properties.getProperty("DATABASENAME");

        }


        public static class Paths{
            public static  String ROOTPATH;
            public static  String TEMPPATH;
            public static InputStream in = null;
            static {
                ROOTPATH = properties.getProperty("ROOTPATH");
                TEMPPATH = properties.getProperty("TEMPPATH");
            }


        }

        public static boolean checkProperties(Set<String> properties){

                if (!properties.containsAll(propertieNames)){
                    LOG.error("Wrong input");
                    return false;
                }

            return true;

        }

    }

