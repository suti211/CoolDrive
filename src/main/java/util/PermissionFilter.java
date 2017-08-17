package util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import controller.UserController;
import dto.User;

/**
 * Created by mudzso on 2017.08.07..
 */

@PreMatching
@Provider
public class PermissionFilter extends ControllersFactory implements ContainerRequestFilter{
    List<String>whiteList = Arrays.asList("/login","/register","/token", "/upload", "/download", "/share");

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        try (UserController userController = getUserController()) {
            Iterator<String> iterator = whiteList.iterator();
            String whiteListItem;
            while (iterator.hasNext()) {
                whiteListItem = iterator.next();
                if (containerRequestContext.getUriInfo().getPath().contains(whiteListItem))
                    return;
            }

            if (containerRequestContext.getMethod().equalsIgnoreCase("options"))
                return;

            String authHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
            String token = null;

            if (authHeader != null && authHeader.contains("Bearer")) {
                token = authHeader.split(" ")[1];
            }

            User user = userController.getUser("token", token);
            if (user == null) {
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                return;
            }

            if (containerRequestContext.getUriInfo().getPath().contains("/adminpage")) {
                if (!user.isAdmin())
                    containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                containerRequestContext.
            }
        }
    }
}
