package org.fortunerise.game.api.v1.resources;

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
import org.fortunerise.game.services.GameBean;
import org.fortunerise.game.services.dtos.BetDto;
import org.fortunerise.game.services.dtos.GameDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@ApplicationScoped
@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Games API", description = "Endpoints for managing game history and operations.")
public class GamesResource {

    @Inject
    private GameBean gameBean;

    @Path("/{userId}")
    @Operation(
            summary = "Start a new game session",
            description = "Creates a new game session for a specified user, using the provided list of bets."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Game successfully created. Returns the details of the created game.",
                    content = @Content(schema = @Schema(implementation = GameDto.class, type = SchemaType.OBJECT))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "The request was invalid, possibly due to invalid user ID or malformed bet data.",
                    content = @Content(schema = @Schema(implementation = String.class, type = SchemaType.STRING))
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "An unexpected error occurred on the server."
            )
    })
    @POST
    public Response createGame(
            @Parameter(
                    name = "userId",
                    description = "The ID of the user for whom the game is being created.",
                    required = true,
                    schema = @Schema(type = SchemaType.INTEGER)
            )
            @PathParam("userId") Integer userId,
            @RequestBody(
                    description = "A list of bets placed by the user, which will be used to create the game.",
                    required = true,
                    content = @Content(schema = @Schema(implementation = BetDto.class, type = SchemaType.ARRAY))
            )
            List<BetDto> betDtos) {
        try {
            GameDto gameDto = gameBean.playGame(userId, betDtos);
            return Response.status(Status.CREATED).entity(gameDto).build();
        } catch (BadRequestException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
