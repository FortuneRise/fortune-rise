package org.fortunerise.api.v1.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

    @Path("/{userId}")
    @POST
    public Response createGame(@PathParam("userId") Integer userId) {


    }

}
