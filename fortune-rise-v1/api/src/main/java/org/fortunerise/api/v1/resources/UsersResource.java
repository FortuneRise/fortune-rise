package org.fortunerise.api.v1.resources;

import org.fortunerise.dtos.UserDto;
import org.fortunerise.beans.UserBean;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    private UserBean userBean;

    @GET
    public Response getUsers() {
        try {
            List<UserDto> userDtos = userBean.getUsers();
            return Response.ok(userDtos).build();
        }
        catch (NoResultException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @Path("{userId}")
    @GET
    public Response getUserByID(@PathParam("userId") Integer userId){
        try {
            UserDto userDto = userBean.getUserDtoById(userId);
            return Response.ok(userDto).build(); // 200 OK if user found
        }
        catch (NoResultException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    public Response createUser(UserDto userDto) {
        try {
           UserDto responseUserDto = userBean.insertUser(userDto);
           return Response.ok(responseUserDto).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }


}
