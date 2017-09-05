package controller;

import dto.Share;
import dto.Status;
import dto.Token;
import dto.UserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.PermissionService;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RequestMapping(
        value = "/share",
        produces = MediaType.APPLICATION_JSON,
        consumes = MediaType.APPLICATION_JSON)
@RestController
public class PermissionController {
    private PermissionService permissionService;
    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Status add(@RequestBody Share share){
        return permissionService.add(share);
    }

    @RequestMapping(value = "/remove",method = RequestMethod.POST)
    public Status remove(@RequestBody Share share){
        return permissionService.remove(share);
    }

    @RequestMapping(value = "/changeAccess",method = RequestMethod.POST)
    public Status changeAccess(@RequestBody Share share){
        return permissionService.changeAccess(share);
    }

    @RequestMapping(value = "sharedWithMe",method = RequestMethod.POST)
        public List<UserFile> sharedWithme(@RequestBody Token token){
        return permissionService.sharedWithMe(token);
    }
    @RequestMapping(value = "sharedFiles",method = RequestMethod.POST)
    public List<UserFile> sharedFiles(@RequestBody Token token){
        return permissionService.sharedFiles(token);
    }
    @RequestMapping(value = "sharedFoler",method = RequestMethod.POST)
    public List<UserFile> sharedFolder(@RequestBody Token token){
        return  permissionService.sharedFolder(token);
    }
    @RequestMapping(value = "sharedWith",method = RequestMethod.POST)
    public List<Share> sharedWith(@RequestBody Token token){
        return permissionService.sharedWith(token);
    }

}
