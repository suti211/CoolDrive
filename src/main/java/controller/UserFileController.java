package controller;

import dto.*;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.UserFileService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@RequestMapping(
        value = "/files",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class UserFileController {
    private UserFileService userFileService;
    @Autowired
    public UserFileController(UserFileService userFileService) {
        this.userFileService = userFileService;
    }

    @RequestMapping(value = "/getFiles",method = RequestMethod.POST)
        public List<UserFile> getAllFiles(@RequestBody  Token token){
            return  userFileService.getAllFilesFromFolder(token);
        }

    @RequestMapping(value = "/deleteFile",method = RequestMethod.POST)
    public Status deleteFile(@RequestBody  Token token){
        Status status = null;
        try {
            status = userFileService.deleteUserFile(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    @RequestMapping(value = "/getStorageInfo",method = RequestMethod.POST)
    public StorageInfo storageInfo(@RequestBody  Token token) {
        return  userFileService.getStorageInfo(token);
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public Status upload(@RequestBody MultipartFormDataInput multipartFormDataInput, HttpServletRequest httpServletRequest) {
        Status status = null;
        try {
            status = userFileService.uploadFile(multipartFormDataInput,httpServletRequest.getContentLength()/1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    @RequestMapping(value = "/uploadTXT",method = RequestMethod.POST)
    public Status uploadTxt(@RequestBody TXT txt){
        Status status = null;
        try {
            status =  userFileService.uploadTXTFile(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
    @RequestMapping(value = "/getTXT",method = RequestMethod.POST)
    public TXT getTxt(@RequestBody Token token){
        TXT txt = null;
        try {
            txt = userFileService.getTXTFile(token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txt;
    }
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public Status modifyFile(@RequestBody UserFile userFile){
        return userFileService.modifyFile(userFile);
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET)
    public Response download(HttpServletRequest httpServletRequest){
        return userFileService.downloadFile(httpServletRequest);
    }

    @RequestMapping(value = "/addPublicLink",method = RequestMethod.POST)
    public Status addPublic(@RequestBody Token token){
        return userFileService.addPublicLink(token);
    }
    @RequestMapping(value = "/deletePublicLink",method = RequestMethod.POST)
    public Status deletePublic(@RequestBody Token token){
        return userFileService.deletePublicLink(token);
    }
    @RequestMapping(value = "/getPublicLink",method = RequestMethod.POST)
    public Status getPublic(@RequestBody Token token){
        return userFileService.getPublicLink(token);
    }

    @RequestMapping(value = "/public",method = RequestMethod.GET)
    public Response downLoadPublic(HttpServletRequest httpServletRequest){
        return userFileService.downloadPublicFile(httpServletRequest);
    }
    @RequestMapping(value = "/createFolder",method = RequestMethod.POST)
    public Status createFolder(@RequestBody Folder folder){
        return userFileService.createFolder(folder);
    }

}
