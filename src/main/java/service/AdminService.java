package service;

import dto.*;
import util.ControllersUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by mudzso on 2017.08.07..
 */
@Path("/adminpage")
public class AdminService extends ControllersUtil{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getAllTransaction(){
        return transactionsController.getAllTransaction();
    }

}
