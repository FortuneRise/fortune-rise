package org.fortunerise.api.v1.resources;

import org.fortunerise.beans.GameBean;
import org.fortunerise.dtos.GameDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HistoryResource {

    @Inject
    private GameBean gameBean;

    @GET
    @Path("{usrId}")
    public Response getHistory(@PathParam("usrId") Integer userId) {
        try{
            List<GameDto> gameHistory = gameBean.getGameDtosByUserId(userId);
            return Response.ok(gameHistory).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
