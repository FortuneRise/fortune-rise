package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.Roulette;
import org.fortunerise.api.v1.models.Bet;
import org.fortunerise.api.v1.models.BetResult;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Path("/roulette")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RouletteResource {

    // Static list to hold active games (for simplicity)
    private static List<Roulette> games = new ArrayList<>();
    private static int gameCounter = 1;

    // POST method to create a new game
    @POST
    public Response createGame(Integer player_id) {
        Roulette game = new Roulette(gameCounter++, player_id);
        games.add(game);
        return Response.status(Status.CREATED).entity(game).build();
    }

    // Helper function to determine if a bet won (mock logic for demonstration)
    private BetResult processBet(Bet bet) {
        Random random = new Random();
        boolean win = random.nextBoolean(); // Random win or lose
        double amount = bet.getAmount() * 2; // Double amount if win, else 0
        return new BetResult(win, amount);
    }

    // POST method to place a bet on an existing game
    @POST
    @Path("/{gameId}")
    public Response placeBet(@PathParam("gameId") int gameId, Bet bet) {
        for (Roulette game : games) {
            if (game.getGameId() == gameId) {
                game.placeBet(bet);
                BetResult result =processBet(bet);
                return Response.ok(result).build(); // Return updated game with new bet
            }
        }
        throw new NotFoundException("Game with ID " + gameId + " not found.");
    }

    // GET method to retrieve game data by game ID
    @GET
    @Path("/{gameId}")
    public Response getGame(@PathParam("gameId") int gameId) {
        for (Roulette game : games) {
            if (game.getGameId() == gameId) {
                return Response.ok(game).build();
            }
        }
        throw new NotFoundException("Game with ID " + gameId + " not found.");
    }

    // DELETE method to delete a game by game ID
    @DELETE
    @Path("/{gameId}")
    public Response deleteGame(@PathParam("gameId") int gameId) {
        for (Roulette game : games) {
            if (game.getGameId() == gameId) {
                games.remove(game);
                return Response.noContent().build(); // 204 No Content on successful deletion
            }
        }
        throw new NotFoundException("Game with ID " + gameId + " not found.");
    }
}
