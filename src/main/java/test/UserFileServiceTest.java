package test;

import dto.Status;
import dto.StorageInfo;
import dto.Token;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.junit.Test;
import service.UserFileService;

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
        String string = "{\"token\":\"asd\",\"id\":\"13\"}";
        Map<String,String> data = new HashMap<>();
        data.put("token","adsdsda");
        data.put("id","123");
        Token token = new Token("asd",123);
        Response storageInfo = target("files/getStorageInfo").request().post(Entity.json(token));
        StorageInfo storageInfo1 = storageInfo.readEntity(StorageInfo.class);
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

}