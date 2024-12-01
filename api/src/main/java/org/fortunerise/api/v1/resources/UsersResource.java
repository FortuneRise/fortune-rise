package org.fortunerise.api.v1.resources;

import org.fortunerise.dtos.UserDto;
import org.fortunerise.beans.UserBean;

import javax.inject.Inject;
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
        List<UserDto> userDtos = userBean.getUsers();

        if (userDtos.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build(); // 204 No Content if no users
        }

        return Response.ok(userDtos).build(); // 200 OK if users are found
    }

    @Path("{usrID}")
    @GET
    public Response getUserByID(@PathParam("usrID") Integer usrID){
        UserDto userDto = userBean.getUserById(usrID);

        if(userDto == null){
            return Response.status(Status.NO_CONTENT).build(); // 204 No Content if user with usrID does not exist
        }

        return Response.ok(userDto).build(); // 200 OK if user found
    }


    @POST
    public Response createUser(UserDto userDto) {
        try {
            // Try to insert the user, this should be a transactional operation
            userBean.insertUser(userDto);

            // If successful, return a 201 Created response
            return Response
                    .status(Response.Status.CREATED)
                    .entity(userDto)  // Optionally return the created object or its ID
                    .build();
        } catch (RuntimeException e) {
            // If a RuntimeException occurs, it will trigger a rollback by the container
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to create user: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Unexpected error: " + e.getMessage())
                    .build();
        }
    }


}
