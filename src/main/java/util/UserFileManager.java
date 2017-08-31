package util;

import controller.UserController;
import controller.UserFileController;
import dto.TXT;
import dto.User;
import dto.UserFile;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by David Szilagyi on 2017. 07. 18..
 */
public class UserFileManager extends SimpleControllersFactory {
    private final Logger LOG = LoggerFactory.getLogger(UserFileManager.class);
    private String folderName;
    private Path rootPath = Paths.get(PathUtil.ROOT_PATH);
    private Path tempPath = Paths.get(PathUtil.TEMP_PATH);

    public void saveUserFile(MultipartFormDataInput input, String token, int parentId, boolean isFolder) {
        try (UserController userController = getUserController();
             UserFileController userFileController = getUserFileController()) {
            User user = userController.getUser("token", token);
            folderName = userFileController.getUserFile(user.getUserHomeId()).getFileName();
            Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
            List<InputPart> inputParts = uploadForm.get("input");
            Map<String, InputStream> streams = new HashMap<>();
            String fileId;
            for (InputPart inputPart : inputParts) {
                try {
                    String fileName = getFileName(inputPart.getHeaders());
                    String extension = fileName.substring(fileName.lastIndexOf("."));
                    InputStream body = inputPart.getBody(new GenericType<>(InputStream.class));
                    fileId = createUserFile(fileName, user, parentId, isFolder) + extension;
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
    }

    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisPosition = header.get("Content-Disposition").get(0).split(";");

        for (String word : contentDisPosition) {
            if ((word.trim().startsWith("filename"))) {
                String[] name = word.split("=");
                return name[1].trim().replaceAll("\"", "");
            }
        }
        return "unknown";
    }

    public boolean saveFolder(String folderName) {
        Path realPath = Paths.get(rootPath.toString(), folderName);
        File file = realPath.toFile();
        if (file.exists() && file.isDirectory()) {
            LOG.debug("Folder is already exists with this name: {}", folderName);
            return false;
        }
        LOG.info("Folder created with this name: {}", folderName);
        return file.mkdirs();
    }

    private void writeFile(String filename, InputStream inputStream) throws IOException {
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
            fileOutputStream.write(bytes, 0, content);
            fileOutputStream.flush();
        }
        fileOutputStream.close();
        setFileSize(file, filename);
    }

    private void writeTXTFile(TXT txt, String fileName) throws IOException {
        LOG.info("writeTXTFile method called with fileName: {}", fileName);
        Path path = Paths.get(rootPath.toString(), folderName, fileName);
        File file = path.toFile();
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        String[] content = txt.getContent().split("\\n");
        for (String line : content) {
            bw.append(line);
            bw.newLine();
        }
        bw.close();
        setFileSize(file, fileName);
    }

    private void setFileSize(File file, String filename) {
        try (UserFileController userFileController = getUserFileController()) {
            int fileId = Integer.valueOf(filename.substring(0, filename.lastIndexOf(".")));
            double size = ((double) file.length()) / 1024;
            size /= 1024;
            userFileController.setFileSize(fileId, size);
        }
    }

    public boolean deleteFile(String path) throws IOException {
        File file = Paths.get(path).toFile();
        if (file.exists()) {
            Files.delete(file.toPath());
            LOG.info("File deleting is successfully done path: {}", path);
            return true;
        }
        LOG.debug("File/Folder with this path : {} does not exist or is not reachable", path);
        return false;
    }

    public boolean createTXTFile(TXT txt, int parentId) {
        LOG.info("createTXTFile method called with parentId: {}, file: {}", parentId, txt.getName());
        try (UserController userController = getUserController();
             UserFileController userFileController = getUserFileController()) {
            User user = userController.getUser("token", txt.getToken().getToken());
            folderName = userFileController.getUserFile(user.getUserHomeId()).getFileName();
            String fileName = txt.getName() + ".txt";
            try {
                int fileId = userFileController.checkUserFile(txt.getName(), ".txt", parentId);
                if (fileId <= 0) {
                    fileId = createUserFile(fileName, user, parentId, false);
                } else {
                    int parentFolder = userFileController.getUserFile(fileId).getParentId();
                    UserFile userHome = userFileController.getUserFile(parentFolder);
                    if (userHome.getParentId() == 1) {
                        folderName = userHome.getFileName();
                    } else {
                        folderName = userFileController.getUserFile(userHome.getParentId()).getFileName();
                    }
                }
                writeTXTFile(txt, fileId + ".txt");
                return true;
            } catch (IOException e) {
                LOG.error("createTXTFile failed with exception", e);
                return false;
            }
        }
    }

    public TXT readFromTXT(String fileName, String path) throws IOException {
        String content = "";
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            content += line + "\n";
        }
        br.close();
        return new TXT(fileName, content);
    }

    private int createUserFile(String fileName, User user, int parentId, boolean isFolder) throws IOException {
        try (UserFileController userFileController = getUserFileController()) {
            Path path = Paths.get(rootPath.toString(), folderName);
            String extension = "dir";
            String name = fileName;
            if (!isFolder) {
                extension = fileName.substring(fileName.lastIndexOf("."));
                name = fileName.substring(0, fileName.lastIndexOf("."));
            }
            UserFile userFile = new UserFile(path.toString(), 0, name, extension, isFolder, user.getId(), parentId);
            return userFileController.addNewUserFile(userFile);
        }
    }

    public void setrootPath(Path path) {
        rootPath = path;
    }

    public File downloadUserFiles(int id) {
        try (UserFileController userFileController = getUserFileController()) {
            byte[] bytes = new byte[1024];
            UserFile userFile = userFileController.getUserFile(id);
            File downloadFile = new File(Paths.get(userFile.getPath() + "\\" + userFile.getId() + userFile.getExtension()).toString());
//        Path temppath = Paths.get(tempPath + "\\" + userFile.getId() + userFile.getExtension());
//        File tempFile = null;
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(temppath.toString());
//            tempFile = new File(temppath.toString());
//            FileInputStream fileInputStream = new FileInputStream(downloadFile);
//            int length;
//            while ((length = fileInputStream.read(bytes)) > 0) {
//                fileOutputStream.write(bytes, 0, length);
//                fileOutputStream.flush();
//            }
//            fileInputStream.close();
//            fileOutputStream.close();
//        } catch (IOException e) {
//        }
            if (downloadFile != null) {
                LOG.info("send file to server with this id: {}", id);
                return downloadFile;
            } else {
                LOG.info("file not found or not available with this id: {}", id);
                return null;
            }
        }
    }
//    public static Response downloadUserFiles(int[] userFileIds, boolean isFolder) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String filename = "arcive" + LocalDateTime.now().format(formatter) + ".zip";
//        byte[] bytes = new byte[1024];
//        String[] userFilePaths = getPathFromUserFiles(userFileIds);
//        Path path = Paths.get(tempPath.toString(), filename);
//        File tempFile = null;
//        if (userFilePaths.length == 1) {
//            tempFile = new File(userFilePaths[0]);
//        } else {
//            try {
//                FileOutputStream fileOutputStream = new FileOutputStream(path.toString());
//                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
//                tempFile = new File(path.toString());
//                for (int i = 0; i < userFilePaths.length; i++) {
//                    tempFile = new File(userFilePaths[i]);
//                    FileInputStream fileInputStream = new FileInputStream(tempFile);
//                    zipOutputStream.putNextEntry(new ZipEntry(tempFile.getName()));
//                    int length;
//                    while ((length = fileInputStream.read(bytes)) > 0) {
//                        zipOutputStream.write(bytes, 0, length);
//                    }
//                    zipOutputStream.closeEntry();
//                    fileInputStream.close();
//                }
//                zipOutputStream.close();
//            } catch (IOException e) {
//
//            }
//        }
//        return Response.ok(tempFile, MediaType.APPLICATION_OCTET_STREAM_TYPE)
//                .header("Content-Disposition", "attachment; filename=\"" + tempFile.getName() + "\"")
//                .build();
//    }

    private String[] getPathFromUserFiles(int[] userFileIds) {
        try (UserFileController userFileController = getUserFileController()) {
            String[] result = new String[userFileIds.length];
            for (int i = 0; i < userFileIds.length; i++) {
                result[i] = userFileController.getUserFile(userFileIds[i]).getPath();
            }
            return result;
        }
    }
}
