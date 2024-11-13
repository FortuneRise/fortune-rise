package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.Bet;
import org.fortunerise.api.v1.models.BetResult;
import org.fortunerise.api.v1.models.Transaction;
import org.fortunerise.api.v1.models.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/history")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HistoryResource {

    // Static variables for previous bets and all transactions
    private static HashMap<Bet, BetResult> previousBets = new HashMap<>();
    private static List<Transaction> transactions = new ArrayList<>();

    // Inicialization with some mock data
    static{
        previousBets.put(new Bet("number", 3.14, 17),new BetResult(true, 31.4));
        previousBets.put(new Bet("color", 1.75, 41),new BetResult(false, 0.0));
        previousBets.put(new Bet("odd/even", 8.99, 21),new BetResult(true, 3000.0));

        transactions.add(new Transaction(new User(1000,"Jan", "jan.rutar11@gmail.com"), new User(1001, "Andraž", "andraz.hribernik@gmail.com"), 1000.0));
        transactions.add(new Transaction(new User(1000,"Jan", "jan.rutar11@gmail.com"), new User(1001, "Andraž", "andraz.hribernik@gmail.com"), 2000.0));
        transactions.add(new Transaction(new User(1001, "Andraž", "andraz.hribernik@gmail.com"), new User(1000,"Jan", "jan.rutar11@gmail.com"), 0.0));
    }


    // Get all user bets
    @GET
    @Path("/games")
    public Response getBets(){
        return Response.ok(previousBets).build();
    }

    // Get all user transactions
    @GET
    @Path("/transactions")
    public Response getTransactions(){
        return Response.ok(transactions).build();
    }

}
