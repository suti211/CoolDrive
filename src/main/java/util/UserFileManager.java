package util;

import controller.UserController;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.rmi.runtime.Log;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by David Szilagyi on 2017. 07. 18..
 */
public class UserFileManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserFileManager.class);
    private static UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);


    public static boolean saveUserFile(MultipartFormDataInput input,String token) {
        String fileName;
        Map<String,List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart>inputParts = uploadForm.get("uploadedFile");
        for(InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte []bytes = IOUtils.toByteArray(inputStream);




            }catch (IOException e){
                LOG.error("save user file failed with Exception",e);
            }
        }
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
        return "unkown";
     }

     public static boolean createFolder(String path,String folderName){
         File file = new File(path+"/"+ folderName);
         if(file.exists() && file.isDirectory())
             return false;
         return file.mkdirs();
     }

     private static void writeFile(byte[] content, String filename)throws IOException{
         File file = new File(filename);
         if (!file.exists()){
             file.createNewFile();
         }
         FileOutputStream fileOutputStream = new FileOutputStream(file);
         fileOutputStream.write(content);
         fileOutputStream.flush();
         fileOutputStream.close();
     }
     public static boolean deleteFile(String path)throws IOException{
         File file = new File(path);
         if (file.exists()){
             Files.delete(file.toPath());
             return true;
         }
         return false;
     }
}
