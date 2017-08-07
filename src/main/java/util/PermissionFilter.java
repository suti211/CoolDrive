package util;

import dto.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mudzso on 2017.08.07..
 */
public class PermissionFilter extends ControllersUtil implements ContainerRequestFilter{
    List<String>whiteList = Arrays.asList("/login","/register");


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Iterator<String> iterator = whiteList.iterator();
        String whiteListItem;
        if (iterator.hasNext()){
            whiteListItem = iterator.next();
            if(containerRequestContext.getUriInfo().getPath().contains(whiteListItem))return;
        }
        String token = containerRequestContext.getHeaderString("token");
        User user = userController.getUser("token",token);
        if (user == null) containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        if (containerRequestContext.getUriInfo().getPath().contains("/adminpage")){
            if (!user.isAdmin())containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }
    }
}
