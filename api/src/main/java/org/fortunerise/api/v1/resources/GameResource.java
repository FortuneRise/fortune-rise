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

@Path("/game")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

    @Inject
    private GameBean gameBean;

    @Path("/{userId}")
    @POST
    public Response createGame(@PathParam("userId") Integer userId, List<BetDto> betDtos) {
        GameDto gameDto = gameBean.playGame(userId, betDtos);

        if (gameDto == null) {
            return Response.status(Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Status.CREATED).entity(gameDto).build();
        }
    }

}
