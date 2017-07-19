package util;

import controller.UserController;
import controller.UserFileController;
import dto.User;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David Szilagyi on 2017. 07. 18..
 */
public class UserFileManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserFileManager.class);
    private static UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);
    private static UserFileController userFileController = new UserFileController(ConnectionUtil.DatabaseName.CoolDrive);
    private  static String folderName;

    public static boolean saveUserFile(MultipartFormDataInput input,String token) {
        User user = userController.getUser(token);
        folderName = userFileController.getUserFile(user.getUserHomeId()).getFileName();

        Map<String,List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart>inputParts = uploadForm.get("uploadedFile");
        Map<String, InputStream> streams = new HashMap<>();
        for(InputPart inputPart : inputParts) {
            try {
                streams.put(getFileName(inputPart.getHeaders()), inputPart.getBody(new GenericType<>(InputStream.class)));
            }   catch (IOException e){
                LOG.error("save user file failed with Exception",e);
            }
        }
        streams.entrySet().parallelStream()
                .forEach(e -> writeFile(e.getKey(), e.getValue()));
        return false;
    }

     private static String getFileName(MultivaluedMap<String, String> header){

        String[] contentDisPosition = header.getFirst("Content-Dispositon").split(";");

        for (String word : contentDisPosition){
            if ((word.trim().startsWith("filename"))){

                String[]name = word.split("=");
                return name[1].trim().replaceAll("\"","");

            }
        }
        return "unknown";
     }

     public static boolean createFolder(String path,String folderName){
         Path realPath = Paths.get(path,folderName);
         File file = realPath.toFile();
         if(file.exists() && file.isDirectory())
             return false;
         return file.mkdirs();
     }

     private static void writeFile(String filename, InputStream inputStream) {
         try {
             Path path = Paths.get(folderName,filename);
             File file = path.toFile();
             if (!file.exists()) {
                 file.createNewFile();
             }
             FileOutputStream fileOutputStream = new FileOutputStream(file);
             byte[] bytes = new byte[1024];
             int content;
             while ((content = inputStream.read(bytes)) != -1) {
                 LOG.debug("writing {} bytes to {}", content, filename);
                 fileOutputStream.write(content);
                 fileOutputStream.flush();
             }


             fileOutputStream.close();
         } catch (IOException e) {
             LOG.error("error when reading remote stream upload",e);
         }
     }
     public static boolean deleteFile(String path)throws IOException{
         File file = Paths.get(path).toFile();
         if (file.exists()){
             Files.delete(file.toPath());
             return true;
         }
         return false;
     }
}
