package test;

import controller.UserController;
import controller.UserFileController;
import dto.UserFile;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.ConnectionUtil;
import util.PathUtil;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mudzso on 2017.08.02..
 */
public class UserFileControllerTest {
    private static final double DELTA = 1e-15;
    private static TestDbManager testDbManager = new TestDbManager(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private static UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive_Test);
    private UserFileController userFileController = new UserFileController(ConnectionUtil.DatabaseName.CoolDrive_Test);
    @BeforeClass
    public static void setUp(){
        testDbManager.fillUsersTableWithDumbData(1);

        testDbManager.fillFilesTableWithDumbData(4,0);
        userController.setHomeId(0,1);
    }
    @Test
    public void getUserFile() throws Exception {
        UserFile userFile1 = userFileController.getUserFile(2);
        assertEquals("asd2",userFile1.getFileName());

    }

    @Test
    public void addNewUserFile() throws Exception {
        userFileController.addNewUserFile(new UserFile(10,PathUtil.ROOT_PATH,100,"","testFile0","jar",0.0,false,0,1,null));
        UserFile result = userFileController.getUserFile(0);
        assertEquals("testFile0",result.getFileName());
    }

    @Test
    public void modifyUserFile() throws Exception {
        UserFile userFile = userFileController.getUserFile(3);
        userFile.setFileName("changed");
        userFile.setMaxSize(100);
        userFileController.modifyUserFile(userFile);
        UserFile result = userFileController.getUserFile(3);
        assertEquals(userFile,result);

    }

    @Test
    public void changeFolderCurrSize() throws Exception {
        UserFile current = userFileController.getUserFile(1);
        assertEquals(10,current.getSize(),DELTA);
        userFileController.changeFolderCurrSize(1,20);
        UserFile result = userFileController.getUserFile(1);
        assertEquals(30,result.getSize(),DELTA);

    }

    @Test
    public void getAllFilesFromFolder() throws Exception {
        List<UserFile> userFiles = userFileController.getAllFilesFromFolder(1);
        List<UserFile> expected = new ArrayList<>();
        expected.add(userFileController.getUserFile(2));
        expected.add(userFileController.getUserFile(3));
        assertEquals(true,userFiles.containsAll(expected));

    }

    @Test
    public void deleteUserFile() throws Exception {
        assertNotNull(userFileController.getUserFile(4));
        userFileController.deleteUserFile(4);
        assertEquals(null,userFileController.getUserFile(4));
    }

    @Test
    public void checkUserFile() throws Exception {
        UserFile userFile = userFileController.getUserFile(2);
        int result = userFileController.checkUserFile(userFile);
        assertEquals(userFile.getId(),result);

    }

    @Test
    public void checkAvailableSpace() throws Exception {
        assertEquals(true,userFileController.checkAvailableSpace(1,40));
        assertEquals(false,userFileController.checkAvailableSpace(1,100000));
    }

    @Test
    public void setFileSize() throws Exception {
        UserFile userFile = userFileController.getUserFile(2);
        assertEquals(10,userFile.getSize(),DELTA);
        userFileController.setFileSize(2,100);
        UserFile result = userFileController.getUserFile(2);
        assertEquals(100,result.getSize(),DELTA);
    }
    @AfterClass
    public static void shutDown(){
        testDbManager.clearDataBase("Users");
        testDbManager.clearDataBase("Files");

    }

}