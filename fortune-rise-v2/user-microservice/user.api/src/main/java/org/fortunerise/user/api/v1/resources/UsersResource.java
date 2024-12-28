package org.fortunerise.user.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.fortunerise.user.services.UserBean;
import org.fortunerise.user.services.UserDto;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Users API", description = "Endpoints for managing users.")
public class UsersResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private UserBean userBean;

    @GET
    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Users retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = UserDto.class, type = SchemaType.ARRAY))),
            @APIResponse(responseCode = "404", description = "No users found."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getUsers() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            List<UserDto> userDtos = userBean.getUsers(queryParameters);
            return Response.ok(userDtos).build();
        }
        catch (NoResultException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "User retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @APIResponse(responseCode = "404", description = "User not found."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getUserByID(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId) {
        try {
            UserDto userDto = userBean.getUserDtoById(userId);
            return Response.ok(userDto).build();
        }
        catch (NoResultException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Operation(summary = "Create a user", description = "Add a new user to the system.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "User created successfully.",
                    content = @Content(schema = @Schema(implementation = UserDto.class))),
            @APIResponse(responseCode = "400", description = "Invalid input."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response createUser(
            @Parameter(name = "UserDto", description = "Details of the user to be created.", required = true) UserDto userDto) {
        try {
            UserDto responseUserDto = userBean.insertUser(userDto);
            return Response.status(Status.CREATED).entity(responseUserDto).build();
        }
        catch (BadRequestException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
