package org.fortunerise.wallet.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
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
@Tag(name = "Wallets API", description = "Endpoints for managing wallets.")
public class WalletsResource {

    @Inject
    private WalletBean walletBean;

    @GET
    @Path("/{userId}")
    @Operation(summary = "Get wallet by user ID", description = "Retrieve a wallet by the user's ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Wallet retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = WalletDto.class))),
            @APIResponse(responseCode = "404", description = "Wallet not found."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getWalletByUsrId(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId) {
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
    @Operation(summary = "Add a wallet for a user", description = "Create a wallet for a specified user.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Wallet created successfully.",
                    content = @Content(schema = @Schema(implementation = WalletDto.class))),
            @APIResponse(responseCode = "400", description = "Wallet with this user ID already exists."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response addWalletToUser(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId) {
        try{
            WalletDto walletDto = walletBean.addWalletForUser(userId);
            return Response.status(Status.CREATED).entity(walletDto).build();
        }
        catch (PersistenceException e) {
            return Response.status(Status.BAD_REQUEST).entity("Wallet with this user id already exists!").build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{userId}")
    @Operation(summary = "Update a wallet by user ID", description = "Update a wallet for a specified user with a transaction.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Wallet updated successfully.",
                    content = @Content(schema = @Schema(implementation = WalletDto.class))),
            @APIResponse(responseCode = "400", description = "Invalid transaction data."),
            @APIResponse(responseCode = "404", description = "Wallet not found."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response updateWallet(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId,
            @Parameter(name = "TransactionDto", description = "Transaction details for the update.", required = true) TransactionDto transactionDto) {
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
