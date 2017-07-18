package util;

import controller.UserController;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by David Szilagyi on 2017. 07. 18..
 */
public class UserFileManager {
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
}
