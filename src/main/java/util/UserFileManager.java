package util;

import controller.UserController;
import controller.UserFileController;
import dto.User;
import dto.UserFile;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by David Szilagyi on 2017. 07. 18..
 */
public class UserFileManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserFileManager.class);
    private static UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);
    private static UserFileController userFileController = new UserFileController(ConnectionUtil.DatabaseName.CoolDrive);
    private static String folderName;
    private static Path rootPath = Paths.get(PathUtil.ROOT_PATH);
    private static Path tempPath = Paths.get(PathUtil.TEMP_PATH);

    public static void saveUserFile(MultipartFormDataInput input, String token, int parentId, boolean isFolder, double maxSize) {
        User user = userController.getUser(token);
        folderName = userFileController.getUserFile(user.getUserHomeId()).getFileName();
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("input");
        Map<String, InputStream> streams = new HashMap<>();
        int fileId = 0;
        for (InputPart inputPart : inputParts) {
            try {
                String fileName = getFileName(inputPart.getHeaders());
                InputStream body = inputPart.getBody(new GenericType<>(InputStream.class));
                fileId = createUserFile(fileName, user, parentId, isFolder, maxSize, body);
                streams.put(String.valueOf(fileId), body);
            } catch (IOException e) {
                LOG.error("The save user method was failed due to an exception", e);
            }
        }
        streams.entrySet().parallelStream().forEach(map -> {
            try {
                writeFile(map.getKey(), map.getValue());
                LOG.info("UserFile is successfully saved from multiPartFormDataInput.File id is: {}", map.getKey());
            } catch (IOException e1) {
                LOG.error("error when reading remote stream upload", e1);
            }
        });
    }

    private static String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisPosition = header.get("Content-Disposition").get(0).split(";");

        for (String word : contentDisPosition) {
            if ((word.trim().startsWith("filename"))) {
                String[] name = word.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    public static boolean saveFolder(String folderName) {
        Path realPath = Paths.get(rootPath.toString(), folderName);
        File file = realPath.toFile();
        if (file.exists() && file.isDirectory()) {
            LOG.debug("Folder is already exists with this name: {}", folderName);
            return false;
        }
        LOG.info("Folder created with this name: {}", folderName);
        return file.mkdirs();
    }

    private static void writeFile(String filename, InputStream inputStream) throws IOException {
        Path path = Paths.get(rootPath.toString(), folderName, filename);
        File file = path.toFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] bytes = new byte[1024];
        int content;
        while ((content = inputStream.read(bytes)) > 0) {
            LOG.debug("writing {} bytes to {}", content, filename);
            fileOutputStream.write(content);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
    }

    public static boolean deleteFile(String path) throws IOException {
        File file = Paths.get(path).toFile();
        if (file.exists()) {
            Files.delete(file.toPath());
            LOG.info("File deleting is succesfully done path: {}", path);
            return true;
        }
        LOG.debug("File/Folder with this path : {} does not exist or is not reachable", path);
        return false;
    }

    private static int createUserFile(String fileName, User user, int parentId, boolean isFolder, double maxSize, InputStream inputStream) throws IOException {
        Path path = Paths.get(rootPath.toString(), folderName);
        double size = (inputStream.available() / 1024) / 1024;
        String extension = "dir";
        String name = fileName;
        if (!isFolder) {
            extension = fileName.substring(fileName.lastIndexOf("."));
            name = fileName.substring(0, fileName.lastIndexOf("."));
        }
        UserFile userFile = new UserFile(path.toString(), size, name, extension, isFolder, user.getId(), parentId);
        return userFileController.addNewUserFile(userFile);
    }

    public static void setrootPath(Path path) {
        rootPath = path;
    }


    public static Response downloadUserFiles(int[] userFileIds, boolean isFolder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String filename = "arcive" + LocalDateTime.now().format(formatter) + ".zip";
        byte[] bytes = new byte[1024];
        String[] userFilePaths = getPathFromUserFiles(userFileIds);
        Path path = Paths.get(tempPath.toString(), filename);
        File tempFile = null;
        if (userFilePaths.length == 1) {
            tempFile = new File(userFilePaths[0]);
        } else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                tempFile = new File(path.toString());
                for (int i = 0; i < userFilePaths.length; i++) {
                    tempFile = new File(userFilePaths[i]);
                    FileInputStream fileInputStream = new FileInputStream(tempFile);
                    zipOutputStream.putNextEntry(new ZipEntry(tempFile.getName()));
                    int length;
                    while ((length = fileInputStream.read(bytes)) > 0) {
                        zipOutputStream.write(bytes, 0, length);
                    }
                    zipOutputStream.closeEntry();
                    fileInputStream.close();
                }
                zipOutputStream.close();
            } catch (IOException e) {

            }
        }
        return Response.ok(tempFile, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .header("Content-Disposition", "attachment; filename=\"" + tempFile.getName() + "\"")
                .build();
    }

    private static String[] getPathFromUserFiles(int[] userFileIds) {
        String[] result = new String[userFileIds.length];
        for (int i = 0; i < userFileIds.length; i++) {
            result[i] = userFileController.getUserFile(userFileIds[i]).getPath();
        }
        return result;
    }
}
