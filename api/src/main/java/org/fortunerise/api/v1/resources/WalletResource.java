package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.WalletModel;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;

@Path("/wallets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WalletResource {

    // Static list to store wallets
    private static List<WalletModel> wallets = new ArrayList<>();

    static {
        // Initialize with some mock data
        wallets.add(new WalletModel(1, 100.0, 1));
        wallets.add(new WalletModel(2, 200.0, 2));
        wallets.add(new WalletModel(3, 300.0, 3));
    }

    // POST method to create a new wallet
    @POST
    public Response createWallet(WalletModel newWallet) {
        newWallet.setId(wallets.size() + 1); // Generate a unique ID
        wallets.add(newWallet);
        return Response.status(Status.CREATED)
                .entity(newWallet)
                .build();
    }

    // GET method to retrieve a specific wallet by ID
    @GET
    @Path("/{id}")
    public Response getWalletById(@PathParam("id") int id) {
        for (WalletModel wallet : wallets) {
            if (wallet.getId() == id) {
                return Response.ok(wallet).build();
            }
        }
        throw new NotFoundException("Wallet with ID " + id + " not found.");
    }

    // PUT method to update an existing wallet by ID
    @PUT
    @Path("/{id}")
    public Response updateWallet(@PathParam("id") int id, WalletModel updatedWallet) {
        for (WalletModel wallet : wallets) {
            if (wallet.getId() == id) {
                wallet.setBalance(updatedWallet.getBalance());
                return Response.ok(wallet).build();
            }
        }
        throw new NotFoundException("Wallet with ID " + id + " not found.");
    }

    // DELETE method to remove a wallet by ID
    @DELETE
    @Path("/{id}")
    public Response deleteWallet(@PathParam("id") int id) {
        for (WalletModel wallet : wallets) {
            if (wallet.getId() == id) {
                wallets.remove(wallet);
                return Response.noContent().build(); // 204 No Content on successful deletion
            }
        }
        throw new NotFoundException("Wallet with ID " + id + " not found.");
    }
}
