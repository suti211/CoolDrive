package service;

import controller.UserController;
import controller.UserFileController;
import dto.StorageInfo;
import dto.Token;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by David Szilagyi on 2017. 07. 18..
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
    @Path("/getStorageInfo")
    public StorageInfo getStorageInfo(Token token, @Context HttpServletRequest request) {
        LOG.info("getStorageInfo method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId(), request.getRemoteAddr());
        UserFile uf = userFileController.getUserFile(getFileId(token, "getStorageInfo"));
        return new StorageInfo(uf.getSize(), uf.getMaxSize());
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
