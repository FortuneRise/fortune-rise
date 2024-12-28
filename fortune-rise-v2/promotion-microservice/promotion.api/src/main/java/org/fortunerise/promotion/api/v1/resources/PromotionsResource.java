package org.fortunerise.promotion.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.fortunerise.promotion.services.PromotionBean;
import org.fortunerise.promotion.services.PromotionDto;
import org.fortunerise.promotion.services.TransactionDto;

import com.kumuluz.ee.rest.beans.QueryParameters;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Promotions API", description = "Endpoints for managing promotions.")
public class PromotionsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    PromotionBean promotionBean;

    @GET
    @Operation(summary = "Get all promotions", description = "Retrieve a list of all promotions.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Promotions retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = PromotionDto.class, type = SchemaType.ARRAY))),
            @APIResponse(responseCode = "400", description = "Invalid query parameters."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getPromotions() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            List<PromotionDto> result = promotionBean.getPromotionDtos(uriInfo);
            return Response.ok(result).build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{userId}/{triggerScenario}")
    @Operation(summary = "Get promotions for a user", description = "Retrieve promotions for a specific user and trigger scenario.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Promotions retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = PromotionDto.class, type = SchemaType.ARRAY))),
            @APIResponse(responseCode = "400", description = "Invalid trigger scenario or parameters."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getPromotionsByUserId(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId,
            @Parameter(name = "triggerScenario", description = "Trigger scenario (all, deposit, or bet).", required = true) @PathParam("triggerScenario") String triggerScenarioParam) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            PromotionBean.TriggerScenario triggerScenario = switch (triggerScenarioParam) {
                case "all" -> PromotionBean.TriggerScenario.ALL;
                case "deposit" -> PromotionBean.TriggerScenario.DEPOSIT;
                case "bet" -> PromotionBean.TriggerScenario.BET;
                default -> throw new IllegalArgumentException("Invalid trigger scenario: " + triggerScenarioParam);
            };

            List<PromotionDto> result = promotionBean.getPromotionDtosByUserId(userId, triggerScenario, uriInfo);
            return Response.ok(result).build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{userId}/{promotionId}")
    @Operation(summary = "Add a promotion to a user", description = "Associate a promotion with a specific user.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Promotion added successfully."),
            @APIResponse(responseCode = "400", description = "Invalid input."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response addPromotionToUser(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId,
            @Parameter(name = "promotionId", description = "The ID of the promotion.", required = true) @PathParam("promotionId") Integer promotionId) {
        try {
            promotionBean.addPromotionToUser(userId, promotionId);
            return Response.noContent().build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{userId}/{promotionId}/verify")
    @Operation(summary = "Verify a promotion", description = "Verify if a promotion can be applied to a transaction.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Verification result.",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @APIResponse(responseCode = "400", description = "Invalid input."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response verifyPromotion(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId,
            @Parameter(name = "promotionId", description = "The ID of the promotion.", required = true) @PathParam("promotionId") Integer promotionId,
            @Parameter(name = "TransactionDto", description = "Transaction details.", required = true) TransactionDto transactionDto) {
        try {
            Boolean result = promotionBean.verifyPromotion(userId, promotionId, transactionDto);
            return Response.ok(result).build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Operation(summary = "Create a promotion", description = "Create a new promotion.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Promotion created successfully.",
                    content = @Content(schema = @Schema(implementation = PromotionDto.class))),
            @APIResponse(responseCode = "400", description = "Invalid input."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response createPromotion(
            @Parameter(name = "PromotionDto", description = "Details of the promotion to be created.", required = true) PromotionDto promotionDto) {
        try {
            PromotionDto result = promotionBean.createPromotion(promotionDto);
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{userId}/{promotionId}")
    @Operation(summary = "Apply a promotion", description = "Apply a promotion to a user's account.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Promotion applied successfully."),
            @APIResponse(responseCode = "400", description = "Invalid input."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response applyPromotion(
            @Parameter(name = "userId", description = "The ID of the user.", required = true) @PathParam("userId") Integer userId,
            @Parameter(name = "promotionId", description = "The ID of the promotion.", required = true) @PathParam("promotionId") Integer promotionId) {
        try {
            return promotionBean.applyPromotion(userId, promotionId);
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
