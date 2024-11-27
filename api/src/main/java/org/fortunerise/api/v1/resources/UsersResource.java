package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.UserModel;

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

    // Static list to store users between calls
    private static List<UserModel> users = new ArrayList<>();

    static {
        // Initialize with some mock data
        users.add(new UserModel(1, "John Doe", "john.doe@example.com"));
        users.add(new UserModel(2, "Jane Smith", "jane.smith@example.com"));
        users.add(new UserModel(3, "Mike Johnson", "mike.johnson@example.com"));
    }

    @GET
    public Response getUsers() {
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        // Search for the user by ID
        for (UserModel user : users) {
            if (user.getId() == id) {
                return Response.ok(user).build(); // Return the user data if found
            }
        }
        // If the user is not found, return a 404 Not Found response
        throw new NotFoundException("User with ID " + id + " not found.");
    }

    @POST
    public Response createUser(UserModel newUser, @Context UriInfo uriInfo) {
        // Set a unique ID for the new user
        newUser.setId(users.size() + 1);

        // Add the new user to the list
        users.add(newUser);

        // Return a 201 Created response with the URI of the new user
        return Response.status(Status.CREATED)
                .entity(newUser)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response updateUser(@PathParam("id") int id, UserModel updatedUser) {
        // Find the user by ID
        for (UserModel user : users) {
            if (user.getId() == id) {
                // Update user fields
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return Response.ok(user).build(); // Return the updated user
            }
        }
        // If user is not found, return a 404 Not Found response
        throw new NotFoundException("User with ID " + id + " not found.");
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int id) {
        // Iterate through the list to find the user by ID
        for (UserModel user : users) {
            if (user.getId() == id) {
                users.remove(user);
                return Response.noContent().build(); // 204 No Content on successful deletion
            }
        }
        // If user is not found, return a 404 Not Found response
        throw new NotFoundException("User with ID " + id + " not found.");
    }
}
