package service;

import dto.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

public interface UserFileService {
    List<UserFile> getAllFilesFromFolder(Token token);

    Status deleteUserFile(Token token) throws IOException;

    StorageInfo getStorageInfo(Token token);

    Status uploadFile(MultipartFormDataInput input, double size) throws IOException;

    Status uploadTXTFile(TXT txt) throws IOException;

    TXT getTXTFile(Token token) throws IOException;

    Status modifyFile(UserFile userFile);

    Response downloadFile(HttpServletRequest request);

    Status addPublicLink(Token token);

    Status deletePublicLink(Token token);

    Status getPublicLink(Token token);

    Response downloadPublicFile(HttpServletRequest request);

    Status createFolder(Folder folder);
}
