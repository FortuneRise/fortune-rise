package org.fortunerise.wallet.api.v1.resources;

import org.fortunerise.wallet.services.TransactionDto;
import org.fortunerise.wallet.services.WalletBean;
import org.fortunerise.wallet.services.WalletDto;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/wallets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WalletsResource {

    @Inject
    private WalletBean walletBean;

    // GET method to retrieve a specific wallet by ID
    @GET
    @Path("/{userId}")
    public Response getWalletByUsrId(@PathParam("userId") Integer userId) {
        try {
            WalletDto walletDto = walletBean.getWalletDtoByUserId(userId);
            return Response.ok(walletDto).build();
        }
        catch (NoResultException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @POST
    @Path("/{userId}")
    public Response addWalletToUser(@PathParam("userId") Integer userId) {
        try{
            WalletDto walletDto = walletBean.addWalletForUser(userId);
            return Response.ok(walletDto).build();
        }
        catch (PersistenceException e) {
            return Response.status(Status.BAD_REQUEST).entity("Wallet with this user id already exists!").build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }




    // PUT method to update an existing wallet by ID
    @PUT
    @Path("/{userId}")
    public Response updateWallet(@PathParam("userId") Integer userId, TransactionDto transactionDto) {

        try {
            System.out.println(transactionDto);
            WalletDto updatedWalletDto = walletBean.updateWalletDto(userId, transactionDto);
            return Response.status(Status.OK).entity(updatedWalletDto).build();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (NoResultException e) {
            return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

    }


}
