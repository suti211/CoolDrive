package test;


import org.junit.Test;
import util.UserFileManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by mudzso on 2017.07.25..
 */
public class UserFileManagerTest {
    private UserFileManager userFileManager = new UserFileManager();

    @Test
    public void saveUserFile() throws Exception {

    }

    @Test
    public void saveFolder() throws Exception {
        userFileManager.setrootPath(Paths.get("/home/mudzso/server"));
        userFileManager.saveFolder("asd");
        assertEquals(true,Files.exists(Paths.get("/home/mudzso/server/asd")));
    }

    @Test
    public void deleteFile() throws Exception {
        String path = "/home/mudzso/server/asd/asd.txt";
        File file = new File(path);
        file.createNewFile();
        userFileManager.deleteFile(path);
        assertEquals(false,Files.exists(Paths.get(path)));
    }

}