package service;

import dao.SimplePermissionsDao;
import dao.SimpleUserDao;
import dao.SimpleUserFileDao;
import dto.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import util.ControllersFactory;
import util.UserFileManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by David Szilagyi und Dani on 2017. 07. 18..
 */
public class SimpleUserFileService implements UserFileService {
    private ControllersFactory controllersFactory;
    private final Logger LOG = LoggerFactory.getLogger(SimpleUserFileService.class);
    private UserFileManager userFileManager;
    @Autowired
    public SimpleUserFileService(ControllersFactory controllersFactory, UserFileManager userFileManager) {
        this.controllersFactory = controllersFactory;
        this.userFileManager = userFileManager;
    }

    @Override
    public List<UserFile> getAllFilesFromFolder(Token token) {
        LOG.info("getAllFilesFromFolder method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId());
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            return simpleUserFileDao.getAllFilesFromFolder(getFileId(token, "getAllFilesFromFolder"));
        }
    }

    @Override
    public Status deleteUserFile(Token token) throws IOException {
        LOG.info("deleteUserFile method is called with token:{}, id: {}", token.getToken(), token.getId());
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            int fileId = getFileId(token, "deleteUserFile");
            UserFile userFile = simpleUserFileDao.getUserFile(fileId);
            if ((userFile.isFolder() && userFile.getSize() == 0) || (!userFile.isFolder())) {
                if (simpleUserFileDao.deleteUserFile(fileId)) {
                    userFileManager.deleteFile(userFile.getPath() + "\\" + userFile.getId() + userFile.getExtension());
                    double fileSize = -userFile.getSize();
                    int parentId = userFile.getParentId();
                    simpleUserFileDao.changeFolderCurrSize(parentId, fileSize);
                    int folderParentId = simpleUserFileDao.getUserFile(parentId).getParentId();
                    if (folderParentId != 1) {
                        simpleUserFileDao.changeFolderCurrSize(folderParentId, fileSize);
                    }
                    return new Status(Operation.USERFILE, true, "Folder/File successfully deleted!");
                }
                return new Status(Operation.USERFILE, false, "There was an error during deleting this folder/file!");
            }
            return new Status(Operation.USERFILE, false, "This folder is not empty!");
        }
    }

    @Override
    public StorageInfo getStorageInfo(Token token) {
        LOG.info("getStorageInfo method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId());
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            UserFile uf = simpleUserFileDao.getUserFile(getFileId(token, "getStorageInfo"));
            return new StorageInfo(uf.getSize(), uf.getMaxSize());
        }
    }

    @Override
    public Status uploadFile(MultipartFormDataInput input, double size) throws IOException {
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            String userToken = input.getParts().get(0).getBodyAsString();
            int id = Integer.valueOf(input.getParts().get(1).getBodyAsString());
            Token token = new Token(userToken, id);
            LOG.info("uploadFile method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId());
            size /= 1024;
            int folderId = getFileId(token, "uploadFile");
            if (simpleUserFileDao.checkAvailableSpace(folderId, size)) {
                userFileManager.saveUserFile(input, token.getToken(), folderId, false);
                simpleUserFileDao.changeFolderCurrSize(folderId, size);
                int parentId = simpleUserFileDao.getUserFile(folderId).getParentId();
                if (parentId != 1) {
                    simpleUserFileDao.changeFolderCurrSize(parentId, size);
                }
                LOG.info("uploadFile method is succeeded with id: {}", id);
                return new Status(Operation.USERFILE, true, "File upload was successful!");
            } else {
                LOG.info("uploadFile method is failed with id: {}", id);
                return new Status(Operation.USERFILE, false, "Not enough space for this file in this folder!");
            }
        }
    }

    @Override
    public Status uploadTXTFile(TXT txt) throws IOException {
        LOG.info("uploadTXTFile method is called with token:{}, id: {}, from: {}", txt.getToken().getToken(), txt.getToken().getId());
        int parentId = getFileId(txt.getToken(), "uploadTXTFile");
        if (userFileManager.createTXTFile(txt, parentId)) {
            return new Status(Operation.TXT, true, "TXT file successfully created!");
        }
        return new Status(Operation.TXT, false, "Cannot create TXT file!");
    }

    @Override
    public TXT getTXTFile(Token token) throws IOException {
        LOG.info("getTXTFile method is called with token:{}, id: {}, from: {}", token.getToken(), token.getId());
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            int fileId = getFileId(token, "getTXTFile");
            UserFile userFile = simpleUserFileDao.getUserFile(fileId);
            String path = userFile.getPath() + "\\" + fileId + ".txt";
            return userFileManager.readFromTXT(userFile.getFileName(), path);
        }
    }

    @Override
    public Status modifyFile(UserFile userFile) {
        LOG.info("modifyFile method is called with id: {}, from: {}", userFile.getId());
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            if (userFile.isFolder() && !simpleUserFileDao.checkAvailableSpace(userFile.getParentId(), userFile.getMaxSize())) {
                LOG.info("modifyFile method is failed with id: {} because of not enough space", userFile.getId());
                return new Status(Operation.USERFILE, false, "Not enough space on the parent folder!");
            } else if (userFile.isFolder() && userFile.getMaxSize() < userFile.getSize()) {
                LOG.info("modifyFile method is failed with id: {} because of wrong maxSize", userFile.getId());
                return new Status(Operation.USERFILE, false, "Max size cannot be lower than current size!");
            }
            if (simpleUserFileDao.modifyUserFile(userFile)) {
                LOG.info("modifyFile method is succeeded with id: {}", userFile.getId());
                return new Status(Operation.USERFILE, true, "Modification was successful!");
            }
            LOG.debug("modifyFile method is failed with id: {}", userFile.getId());
            return new Status(Operation.USERFILE, false, "There was an error on modification!");
        }
    }

    private int getFileId(Token token, String methodName) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController()) {
            String userToken = token.getToken();
            int fileId = token.getId();
            LOG.info("getFileId method is called with token:{}, id: {}, from: {}", userToken, fileId, methodName);
            if (fileId <= 0) {
                fileId = simpleUserDao.getUser("token", userToken).getUserHomeId();
            }
            return fileId;
        }
    }

    @Override
    public Response downloadFile(HttpServletRequest request) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController();
             SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController();
             SimplePermissionsDao permissionsController = controllersFactory.getPermissionsController()) {
            int id = Integer.valueOf(request.getParameter("id"));
            String token = request.getParameter("token");
            LOG.info("downloadFile method is called with id: {}, from: {}", id, request.getRemoteAddr());
            int userId = simpleUserDao.getUser("token", token).getId();
            if (permissionsController.checkAccess(id, userId) ||
                    simpleUserFileDao.getUserFile(id).getOwnerId() == userId) {
                File userFile = userFileManager.downloadUserFiles(Integer.valueOf(id));
                String name = userFile.getName();
                String fileName = simpleUserFileDao.getUserFile(id).getFileName() + name.substring(name.lastIndexOf("."));
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
            return Response.noContent().header("Access Denied", "You don't have access to download this file")
                    .build();
        }
    }


    @Override
    public Status addPublicLink(Token token) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController();
             SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            int fileId = token.getId();
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            if (simpleUserFileDao.setPublicLink(fileId, userId)) {
                return new Status(Operation.USERFILE, true, "Public link successfully generated");
            } else {
                return new Status(Operation.USERFILE, false, "Public link cannot be added to this file");
            }
        }
    }

    @Override
    public Status deletePublicLink(Token token) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController();
             SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            int fileId = token.getId();
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            if (simpleUserFileDao.deletePublicLink(fileId, userId)) {
                return new Status(Operation.USERFILE, true, "Public link successfully removed");
            } else {
                return new Status(Operation.USERFILE, false, "Public link cannot be removed from this file");
            }
        }
    }

    @Override
    public Status getPublicLink(Token token) {
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController();
             SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            int fileId = token.getId();
            int userId = simpleUserDao.getUser("token", token.getToken()).getId();
            String publicLink = simpleUserFileDao.getPublicLink(fileId, userId);
            return new Status(Operation.USERFILE, true, publicLink);
        }
    }

    @Override
    public Response downloadPublicFile(HttpServletRequest request) {
        String publicLink = request.getParameter("link");
        try (SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            LOG.info("downloadPublicFile method is called with this publicLink: {}, from: {}", publicLink);
            int id = simpleUserFileDao.getPublicUserFile(publicLink);
            if (id != -1) {
                File userFile = userFileManager.downloadUserFiles(Integer.valueOf(id));
                String name = userFile.getName();
                String fileName = simpleUserFileDao.getUserFile(id).getFileName() + name.substring(name.lastIndexOf("."));
                if (userFile != null) {
                    LOG.info("File is found and ready to send to user with this id: {}", id);
                    return Response.ok(userFile, MediaType.APPLICATION_OCTET_STREAM_TYPE)
                            .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                            .build();
                }
            }
        }
        LOG.error("File is not available or not found with this publicLink: {}", publicLink);
        return Response.noContent()
                .header("No-Content", "This download link is not valid!")
                .build();
    }

    @Override
    public Status createFolder(Folder folder) {
        LOG.info("createFolder method is called with id: {}, from: {}");
        try (SimpleUserDao simpleUserDao = controllersFactory.getUserController();
             SimpleUserFileDao simpleUserFileDao = controllersFactory.getUserFileController()) {
            Token token = new Token(folder.getToken(), -1);
            int parentId = getFileId(token, "createFolder");
            User user = simpleUserDao.getUser("token", token.getToken());
            UserFile userFile = simpleUserFileDao.getUserFile(parentId);
            String path = userFile.getPath() + "\\" + userFile.getFileName();
            if (simpleUserFileDao.checkAvailableSpace(parentId, folder.getMaxSize())) {
                int id = simpleUserFileDao.addNewUserFile(new UserFile(path, 0, folder.getName(), "dir", folder.getMaxSize(), true, user.getId(), parentId, folder.getLabel()));
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
}
