package org.fortunerise.api.v1.resources;

import org.fortunerise.beans.GameBean;
import org.fortunerise.dtos.BetDto;
import org.fortunerise.dtos.GameDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GamesResource {

    @Inject
    private GameBean gameBean;

    @Path("/{userId}")
    @POST
    public Response createGame(@PathParam("userId") Integer userId, List<BetDto> betDtos) {
        try {
            GameDto gameDto = gameBean.playGame(userId, betDtos);
            return Response.status(Status.CREATED).entity(gameDto).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
