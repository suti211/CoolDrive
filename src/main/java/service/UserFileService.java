package service;

import controller.UserController;
import controller.UserFileController;
import dto.Operation;
import dto.User;
import dto.UserFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConnectionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

    public List<UserFile> getAllFilesFromFolder(String token, int id, @Context HttpServletRequest request) {
        LOG.info("getAllFilesFromFolder post method is called with token:{}, id: {}, from: {}", token, id, request.getRemoteAddr());
        int fileId = id;
        if (id != -1) {
            fileId = userController.getUser(token).getId();
        }
        return userFileController.getAllFilesFromFolder(fileId);
    }
}
