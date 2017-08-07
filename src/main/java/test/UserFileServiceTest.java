package test;

import dto.Status;
import dto.StorageInfo;
import dto.Token;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import service.UserFileService;
import util.ConnectionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by mudzso on 2017.08.03..
 */
public class UserFileServiceTest extends JerseyTest{
    private static TestDbManager testDbManager = new TestDbManager(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private static final double DELTA = 1e-15;

    @BeforeClass
    public static void settingUp(){
        testDbManager.fillUsersTableWithDumbData(3);
        testDbManager.fillFilesTableWithDumbData(2,1);
    }
    @Override
    protected javax.ws.rs.core.Application configure(){
        return new ResourceConfig(UserFileService.class);
    }
    @Test
    public void getAllFilesFromFolder() throws Exception {


    }

    @Test
    public void deleteUserFile() throws Exception {
    }

    @Test
    public void getStorageInfo() throws Exception {
        Map<String,String> data = new HashMap<>();
        data.put("token","asd0");
        data.put("id","1");
        Token token = new Token("asd",123);
        StorageInfo storageInfo = target("files/getStorageInfo").request().post(Entity.json(token)).readEntity(StorageInfo.class);
        assertEquals(10,storageInfo.getUsage(),DELTA);
    }

    @Test
    public void uploadFile() throws Exception {


    }

    @Test
    public void modifyFile() throws Exception {
    }

    @Test
    public void downloadFile() throws Exception {
    }

    @Test
    public void createFolder() throws Exception {
    }

    @AfterClass
    public static void shutDown(){
        testDbManager.clearDataBase("Files");
        testDbManager.clearDataBase("Users");
    }

}