/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//import javax.enterprise.context.RequestScoped;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import model.Status;


@Path("/members")
//@RequestScoped
public class Member {

	@GET
	@Path("/hello")
	public String sayHello() {
		return "<h1>Kaka</h1>";
	}

	@POST
	@Path("/hello")
	public Response getHello(@FormParam("message") String msg) {
		return Response.status(200).entity("asdasdasd: " + msg).build();
	}
	
	@GET
	@Path("/users?query=shit")
	public String getUsers(@QueryParam("shit") String msg){
		return msg;
	}
	
	@POST
	@Path("/login")
	@Produces("application/json")
	@Consumes("application/json")
	public Status loginTest(String json){
		System.out.println("rekveszt jött");
		System.out.println(json);
		
		Status stat = new Status("Login", false);
		
		Map<String, String> users = new HashMap<String, String>();
		users.put("asd", "123");
		
		return stat;
	}
	
	
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String listAllMembers() {
//        return "teszt";
//    }
//
//
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createMember(Member member) {
//
//        Response.ResponseBuilder builder = null;
//
//        try {
//            // Validates member using bean validation
//            validateMember(member);
//
//            registration.register(member);
//
//            // Create an "ok" response
//            builder = Response.ok();
//        } catch (ConstraintViolationException ce) {
//            // Handle bean validation issues
//            builder = createViolationResponse(ce.getConstraintViolations());
//        } catch (ValidationException e) {
//            // Handle the unique constrain violation
//            Map<String, String> responseObj = new HashMap<>();
//            responseObj.put("email", "Email taken");
//            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
//        } catch (Exception e) {
//            // Handle generic exceptions
//            Map<String, String> responseObj = new HashMap<>();
//            responseObj.put("error", e.getMessage());
//            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
//        }
//
//        return builder.build();
//    }


    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     * 
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
//    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
//        System.out.println(("Validation completed. violations found: " + violations.size());
//
//        Map<String, String> responseObj = new HashMap<>();
//
//        for (ConstraintViolation<?> violation : violations) {
//            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
//        }
//
//        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
//    }


}
