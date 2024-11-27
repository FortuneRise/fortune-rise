package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.BetModel;
import org.fortunerise.api.v1.models.BetResultModel;
import org.fortunerise.api.v1.models.TransactionModel;
import org.fortunerise.api.v1.models.UserModel;

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
    private static HashMap<BetModel, BetResultModel> previousBets = new HashMap<>();
    private static List<TransactionModel> transactions = new ArrayList<>();

    // Inicialization with some mock data
    static{
        previousBets.put(new BetModel("number", 3.14, 17),new BetResultModel(true, 31.4));
        previousBets.put(new BetModel("color", 1.75, 41),new BetResultModel(false, 0.0));
        previousBets.put(new BetModel("odd/even", 8.99, 21),new BetResultModel(true, 3000.0));

        transactions.add(new TransactionModel(new UserModel(1000,"Jan", "jan.rutar11@gmail.com"), new UserModel(1001, "Andraž", "andraz.hribernik@gmail.com"), 1000.0));
        transactions.add(new TransactionModel(new UserModel(1000,"Jan", "jan.rutar11@gmail.com"), new UserModel(1001, "Andraž", "andraz.hribernik@gmail.com"), 2000.0));
        transactions.add(new TransactionModel(new org.fortunerise.api.v1.models.UserModel(1001, "Andraž", "andraz.hribernik@gmail.com"), new UserModel(1000,"Jan", "jan.rutar11@gmail.com"), 0.0));
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
