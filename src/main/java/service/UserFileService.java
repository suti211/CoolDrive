package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/files")
public class UserFileService {
    @POST
    @Path("/upload")
    @Consumes("multipart/from-data")
    public Response upload(MultipartFormDataInput input){

        return Response.status(200);
    }
}
