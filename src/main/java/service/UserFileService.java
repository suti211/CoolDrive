package service;

import controller.UserController;
import controller.UserFileController;
import dto.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import util.UserFileManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by David Szilagyi und Dani on 2017. 07. 18..
 */
@Path("/files")
public class UserFileService {
    private static final Logger LOG = LoggerFactory.getLogger(UserFileService.class);
    private static final UserController userController = new UserController(ConnectionUtil.DatabaseName.CoolDrive);
    private static final UserFileController userFileController = new UserFileController(ConnectionUtil.DatabaseName.CoolDrive);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getFiles")
    public List<UserFile> getAllFilesFromFolder(Token token, @Context HttpServletRequest request) {
        LOG.info("getAllFilesFromFolder method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId(), request.getRemoteAddr());
        return userFileController.getAllFilesFromFolder(getFileId(token, "getAllFilesFromFolder"));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteFile")
    public boolean deleteUserFile(Token token, @Context HttpServletRequest request) throws IOException {
        LOG.info("deleteUserFile method is called with token:{}, id: {}", token.getToken(), token.getId());
        int fileId = getFileId(token, "deleteUserFile");
        UserFile userFile = userFileController.getUserFile(fileId);
        UserFileManager.deleteFile(userFile.getPath() + "\\" + userFile.getId() + userFile.getExtension());
        double fileSize = -userFile.getSize();
        int parentId = userFile.getParentId();
        userFileController.changeFolderCurrSize(parentId, fileSize);
        int folderParentId = userFileController.getUserFile(parentId).getParentId();
        if (folderParentId != 1) {
            userFileController.changeFolderCurrSize(folderParentId, -fileSize);
        }
        return userFileController.deleteUserFile(token.getId());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getStorageInfo")
    public StorageInfo getStorageInfo(Token token, @Context HttpServletRequest request) {
        LOG.info("getStorageInfo method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId(), request.getRemoteAddr());
        UserFile uf = userFileController.getUserFile(getFileId(token, "getStorageInfo"));
        return new StorageInfo(uf.getSize(), uf.getMaxSize());
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public Status uploadFile(MultipartFormDataInput input, @Context HttpServletRequest request) throws IOException {
        String userToken = input.getParts().get(0).getBodyAsString();
        int id = Integer.valueOf(input.getParts().get(1).getBodyAsString());
        Token token = new Token(userToken, id);
        LOG.info("uploadFile method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId(), request.getRemoteAddr());
        double size = (request.getContentLength() / 1024);
        size /= 1024;
        int folderId = getFileId(token, "uploadFile");
        if (userFileController.checkAvailableSpace(folderId, size)) {
            UserFileManager.saveUserFile(input, token.getToken(), folderId, false);
            userFileController.changeFolderCurrSize(folderId, size);
            int parentId = userFileController.getUserFile(folderId).getParentId();
            if (parentId != 1) {
                userFileController.changeFolderCurrSize(parentId, size);
            }
            LOG.info("uploadFile method is succeeded with id: {}", id);
            return new Status(Operation.USERFILE, true, "File upload was successful!");
        } else {
            LOG.info("uploadFile method is failed with id: {}", id);
            return new Status(Operation.USERFILE, false, "Not enough space for this file in this folder!");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/uploadTXT")
    public Status uploadTXTFile(TXT txt, @Context HttpServletRequest request) throws IOException {
        int parentId = getFileId(txt.getToken(), "uploadTXTFile");
        if(UserFileManager.createTXTFile(txt, parentId)) {
            return new Status(Operation.TXT, true, "TXT file successfully created!");
        }
        return new Status(Operation.TXT, false, "Cannot create TXT file!");
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modify")
    public Status modifyFile(UserFile userFile, @Context HttpServletRequest request) {
        LOG.info("modifyFile method is called with id: {}, from: {}", userFile.getId(), request.getRemoteAddr());
        if(userFile.isFolder() && !userFileController.checkAvailableSpace(userFile.getParentId(), userFile.getMaxSize())) {
            LOG.info("modifyFile method is failed with id: {} because of not enough space", userFile.getId());
            return new Status(Operation.USERFILE, false, "Not enough space on the parent folder!");
        } else if(userFile.isFolder() && userFile.getMaxSize() < userFile.getSize()) {
            LOG.info("modifyFile method is failed with id: {} because of wrong maxSize", userFile.getId());
            return new Status(Operation.USERFILE, false, "Max size cannot be lower than current size!");
        }
        if (userFileController.modifyUserFile(userFile)) {
            LOG.info("modifyFile method is succeeded with id: {}", userFile.getId());
            return new Status(Operation.USERFILE, true, "Modification was successful!");
        }
        LOG.debug("modifyFile method is failed with id: {}", userFile.getId());
        return new Status(Operation.USERFILE, false, "There was an error on modification!");
    }

    private int getFileId(Token token, String methodName) {
        String userToken = token.getToken();
        int fileId = token.getId();
        LOG.info("getFileId method is called with token:{}, id: {}, from: {}", userToken, fileId, methodName);
        if (fileId <= 0) {
            fileId = userController.getUser("token", userToken).getUserHomeId();
        }
        return fileId;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/download")
    public Response downloadFile(@Context HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        LOG.info("downloadFile method is called with id: {}, from: {}", id, request.getRemoteAddr());
        File userFile = UserFileManager.downloadUserFiles(Integer.valueOf(id));
        String name = userFile.getName();
        String fileName = userFileController.getUserFile(id).getFileName() + name.substring(name.lastIndexOf("."));
        if (userFile != null) {
            LOG.info("File is found and ready to send to user with this id: {}", id);
            return Response.ok(userFile, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .build();
        } else {
            LOG.error("File is not available or not found with this id: {}", request.getParameter("id"));
            return null;
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/createFolder")
    public Status createFolder(Folder folder, @Context HttpServletRequest request) {
        LOG.info("createFolder method is called with id: {}, from: {}", request.getRemoteAddr());
        Token token = new Token(folder.getToken(), -1);
        int parentId = getFileId(token, "createFolder");
        User user = userController.getUser("token", token.getToken());
        UserFile userFile = userFileController.getUserFile(parentId);
        String path = userFile.getPath() + "\\" + userFile.getFileName();
        if (userFileController.checkAvailableSpace(parentId, folder.getMaxSize())) {
            int id = userFileController.addNewUserFile(new UserFile(path, 0, folder.getName(), "dir", folder.getMaxSize(), true, user.getId(), parentId));
            if (id > 0) {
                LOG.info("Folder added to this path: {} with this id: {}", path, id);
                return new Status(Operation.CREATEFOLDER, true, "Folder successfully created!");
            } else {
                LOG.info("Folder failed to this path: {} with this id: {}", path, id);
                return new Status(Operation.CREATEFOLDER, false, "Folder creation is failed!");
            }
        } else {
            LOG.info("User({}) not enough space in this path: {}", user.getUserName(), path);
            return new Status(Operation.CREATEFOLDER, false, "Not enough space for this folder!");
        }
    }
}
