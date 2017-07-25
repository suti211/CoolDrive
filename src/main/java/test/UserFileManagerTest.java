package test;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import javax.inject.Inject;
import org.junit.runner.RunWith;
import util.UserFileManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created by mudzso on 2017.07.25..
 */
@RunWith(Arquillian.class)
public class UserFileManagerTest {
    @Inject
    UserFileManager userFileManager;

    @org.junit.Test
    public void saveUserFile() throws Exception {

    }

    @org.junit.Test
    public void saveFolder() throws Exception {
    }

    @org.junit.Test
    public void deleteFile() throws Exception {
    }

    @org.junit.Test
    public void setrootPath() throws Exception {
        Path path = Paths.get("/home/test");
        userFileManager.setrootPath(path);
        assertEquals(true, Files.exists(path));
    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(util.UserFileManager.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

}
