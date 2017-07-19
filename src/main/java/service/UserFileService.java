package service;

import com.google.gson.Gson;
import controller.UserController;
import controller.UserFileController;
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
    @Path("/getfiles")
    public List<UserFile> getAllFilesFromFolder(Token token, @Context HttpServletRequest request) {
        String userToken = token.getToken();
        int fileId = token.getId();
        System.out.println(userToken);
        System.out.println(fileId);
        LOG.info("getAllFilesFromFolder post method is called with token:{}, id: {}, from: {}", userToken, fileId, request.getRemoteAddr());
        if (fileId <= 0) {
            fileId = userController.getUser(userToken).getUserHomeId();
        }
        return userFileController.getAllFilesFromFolder(fileId);
    }
}
