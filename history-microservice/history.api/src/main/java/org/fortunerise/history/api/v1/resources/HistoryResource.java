package org.fortunerise.history.api.v1.resources;

import org.fortunerise.history.entities.Bet;
import org.fortunerise.history.services.BetDto;
import org.fortunerise.history.services.HistoryBean;
import org.fortunerise.history.services.GameDto;
import org.fortunerise.history.services.TransactionDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("/history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "History API", description = "Endpoints for managing game and transaction history.")
public class HistoryResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private HistoryBean historyBean;

    private Logger log = Logger.getLogger(HistoryResource.class.getName());

    @GET
    @Path("/games/{userId}")
    @Operation(
            summary = "Retrieve game history",
            description = "Fetches the game history for a given user."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "List of games for the user.",
                    content = @Content(schema = @Schema(implementation = GameDto.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No games found for the given user."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response getGameHistory(
            @Parameter(
                    name = "userId",
                    description = "The ID of the user whose game history is being retrieved.",
                    required = true,
                    schema = @Schema(type = SchemaType.INTEGER)
            )
            @PathParam("userId") Integer userId) {
        try {
            QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
            List<GameDto> gameHistory = historyBean.getGameDtosByUserId(userId, query);
            Long gameTotalCount = historyBean.getGameCount(userId, query);
            return Response.ok(gameHistory).header("X-Total-Count", gameTotalCount).build();
        } catch (NoResultException e) {
            log.info(e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/games/{gameId}/bets")
    @Operation(
            summary = "Retrieve bets for a specified game.",
            description = "Fetches the bets given from a game id."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "List of bets for the game.",
                    content = @Content(schema = @Schema(implementation = BetDto.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No bets found for the given game id."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response getGameHistoryBets(
            @Parameter(
            name = "gameId",
            description = "The ID of the game whose bets are being retrieved.",
            required = true,
            schema = @Schema(type = SchemaType.INTEGER)
            )@PathParam("gameId") Integer gameId) {
        try {
            QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
            List<BetDto> gameHistory = historyBean.getBetDtosByGameId(gameId, query);
            return Response.ok(gameHistory).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/games/{userId}")
    @Operation(
            summary = "Add a game to user history",
            description = "Creates a new game entry for a user."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "The created game entry.",
                    content = @Content(schema = @Schema(implementation = GameDto.class))
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response createGameHistory(
            @Parameter(
                    name = "userId",
                    description = "The ID of the user whose game history is being updated.",
                    required = true,
                    schema = @Schema(type = SchemaType.INTEGER)
            )
            @PathParam("userId") Integer userId,
            @RequestBody(
                    description = "The game details to be added.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = GameDto.class))
            )
            GameDto gameDto) {
        try {
            GameDto createdGameDto = historyBean.addGameByUserId(userId, gameDto);
            return Response.status(Response.Status.CREATED).entity(createdGameDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/transactions/{userId}")
    @Operation(
            summary = "Retrieve transaction history",
            description = "Fetches the transaction history for a given user."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "List of transactions for the user.",
                    content = @Content(schema = @Schema(implementation = TransactionDto.class, type = SchemaType.ARRAY))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No transactions found for the given user."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response getTransactionHistory(
            @Parameter(
                    name = "userId",
                    description = "The ID of the user whose transaction history is being retrieved.",
                    required = true,
                    schema = @Schema(type = SchemaType.INTEGER)
            )
            @PathParam("userId") Integer userId) {
        try {
            QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
            List<TransactionDto> transactionHistory = historyBean.getTransactionDtosByUseId(userId, query);
            Long transactionTotalCount = historyBean.getTransactionCount(userId, query);
            return Response.ok(transactionHistory).header("X-Total-Count", transactionTotalCount).build();
        } catch (NoResultException e) {

            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/transactions/{userId}")
    @Operation(
            summary = "Add a transaction to user history",
            description = "Creates a new transaction entry for a user."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "The created transaction entry.",
                    content = @Content(schema = @Schema(implementation = TransactionDto.class))
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response createTransactionHistory(
            @Parameter(
                    name = "userId",
                    description = "The ID of the user whose transaction history is being updated.",
                    required = true,
                    schema = @Schema(type = SchemaType.INTEGER)
            )
            @PathParam("userId") Integer userId,
            @RequestBody(
                    description = "The transaction details to be added.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TransactionDto.class))
            )
            TransactionDto transactionDto) {
        try {
            TransactionDto createdTransactionDto = historyBean.addTransactionByUser(userId, transactionDto);
            return Response.status(Response.Status.CREATED).entity(createdTransactionDto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
