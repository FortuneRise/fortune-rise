package org.fortunerise.api.v1.resources;

import org.fortunerise.beans.WalletBean;
import org.fortunerise.dtos.WalletDto;

import javax.inject.Inject;
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
    @Path("/{usrid}")
    public Response getWalletByUsrId(@PathParam("usrid") Integer usrID) {
        WalletDto walletDto = walletBean.getWalletByUserId(usrID);

        if (walletDto == null){
            return Response.status(Status.NO_CONTENT).build();
        }

        return Response.ok(walletDto).build();
    }

    // PUT method to update an existing wallet by ID
    @PUT
    @Path("/{usrid}")
    public Response updateWallet(WalletDto walletAddBalance, @PathParam("usrid") Integer id) {
        if(walletBean.updateWallet(id, walletAddBalance.getBalance())){
            return Response.ok().build();
        }

        return Response.notModified().build();
    }

}
