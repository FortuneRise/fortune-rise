package org.fortunerise.api.v1.resources;

import org.fortunerise.beans.WalletBean;
import org.fortunerise.dtos.WalletDto;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

@Path("/wallets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WalletResource {

    @Inject
    private WalletBean walletBean;

    // GET method to retrieve a specific wallet by ID
    @GET
    @Path("/{userId}")
    public Response getWalletByUsrId(@PathParam("userId") Integer userId) {
        try {
            WalletDto walletDto = walletBean.getWalletByUserId(userId);

            return Response.ok(walletDto).build();
        } catch (NoResultException e) {
            return Response.status(Status.NO_CONTENT).build();
        }

    }

    // PUT method to update an existing wallet by ID
    @PUT
    @Path("/{usrid}")
    public Response updateWallet(WalletDto walletAddBalance, @PathParam("usrid") Integer id) {

        try {
            if(walletBean.updateWallet(id, walletAddBalance.getBalance())){
                return Response.ok().build();
            }
            return Response.notModified().build();
        }catch (NoResultException e){
            return Response.status(Status.NO_CONTENT).build();
        }

    }

}
