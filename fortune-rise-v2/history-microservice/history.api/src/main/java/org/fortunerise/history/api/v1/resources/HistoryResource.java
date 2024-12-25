package org.fortunerise.history.api.v1.resources;

import org.fortunerise.history.services.HistoryBean;
import org.fortunerise.dtos.GameDto;
import org.fortunerise.dtos.TransactionDto;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HistoryResource {

    @Inject
    private HistoryBean historyBean;

    @GET
    @Path("/games/{usrId}")
    public Response getGameHistory(@PathParam("usrId") Integer userId) {
        try{
            List<GameDto> gameHistory = historyBean.getGameDtosByUserId(userId);
            return Response.ok(gameHistory).build();
        }
        catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/transactions/{usrId}")
    public Response getTransactionHistory(@PathParam("usrId") Integer userId) {
        try{
            List<TransactionDto> transactionHistory = historyBean.getTransactionDtosByUseId(userId);
            return Response.ok(transactionHistory).build();
        }
        catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
