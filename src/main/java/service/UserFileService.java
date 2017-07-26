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
        LOG.info("deleteUserfILE method is called with token:{}, id: {}", token.getToken(), token.getId());
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
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/upload")
    public Status uploadFile(MultipartFormDataInput input, Token token, boolean isFolder, double maxSize, @Context HttpServletRequest request) {
        LOG.info("uploadFile method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId(), request.getRemoteAddr());
        String userToken = token.getToken();
        int folderId = getFileId(token, "uploadFile");
        UserFileManager.saveUserFile(input, userToken, folderId, isFolder, maxSize);
        return null;
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
