package service;

import controller.UserController;
import controller.UserFileController;
import dto.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import util.UserFileManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by David Szilagyi und Dani on 2017. 07. 18..
 */
@Path("/files")
public class UserFileService {
    private static final Logger LOG = LoggerFactory.getLogger(RegisterService.class);
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
    public boolean deleteUserFile(Token token, @Context HttpServletRequest request) {
        LOG.info("deleteUserFile method is called with token:{}, id: {}", token.getToken(), token.getId());
        int fileId = getFileId(token, "deleteUserFile");
        double size = userFileController.getUserFile(fileId).getSize();
        userFileController.changeFolderCurrSize(fileId, -size);
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
        double size = (request.getContentLength() / 1024) / 1024;
        int folderId = getFileId(token, "uploadFile");
        if(userFileController.checkAvailableSpace(folderId, size)) {
            UserFileManager.saveUserFile(input, token.getToken(), folderId, false, 0);
            userFileController.changeFolderCurrSize(folderId, size);
            return new Status(Operation.USERFILE, true, "success");
        } else {
            return new Status(Operation.USERFILE, false, "not enough space");
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/modify")
    public Status modifyFile(UserFile userFile, @Context HttpServletRequest request) {
        LOG.info("modifyFile method is called with id: {}, from: {}", userFile.getId(), request.getRemoteAddr());
        if (userFileController.modifyUserFile(userFile)) {
            LOG.info("modifyFile method is succeeded with id: {}", userFile.getId());
            return new Status(Operation.USERFILE, true, "success");
        }
        LOG.debug("modifyFile method is failed with id: {}", userFile.getId());
        return new Status(Operation.USERFILE, false, "failed");
    }

    private int getFileId(Token token, String methodName) {
        String userToken = token.getToken();
        int fileId = token.getId();
        LOG.info("getFileId method is called with token:{}, id: {}, from: {}", userToken, fileId, methodName);
        if (fileId <= 0) {
            fileId = userController.getUser(userToken).getUserHomeId();
        }
        return fileId;
    }
}
