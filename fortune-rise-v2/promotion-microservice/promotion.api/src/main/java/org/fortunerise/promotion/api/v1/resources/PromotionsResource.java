package org.fortunerise.promotion.api.v1.resources;


import org.fortunerise.promotion.services.PromotionBean;
import org.fortunerise.promotion.services.PromotionDto;
import org.fortunerise.promotion.services.TransactionDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.kumuluz.ee.rest.beans.QueryParameters;
import java.util.List;

@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    PromotionBean promotionBean;

    @GET
    public Response getPromotions() {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            List<PromotionDto> result = promotionBean.getPromotionDtos(queryParameters);
            return Response.ok(result).build();
        } catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{userId}/{triggerScenario}")
    public Response getPromotionsByUserId(@PathParam("userId") Integer userId, @PathParam("triggerScenario") String triggerScenarioParam) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            PromotionBean.TriggerScenario triggerScenario = switch (triggerScenarioParam) {
                case "all" -> PromotionBean.TriggerScenario.ALL;
                case "deposit" -> PromotionBean.TriggerScenario.DEPOSIT;
                case "bet" -> PromotionBean.TriggerScenario.BET;
                default -> throw new IllegalArgumentException("Invalid trigger scenario: " + triggerScenarioParam);
            };

            List<PromotionDto> result = promotionBean.getPromotionDtosByUserId(userId, triggerScenario);

            return Response.ok(result).build();
        } catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{userId}/{promotionId}")
    public Response addPromotionToUser(@PathParam("userId") Integer userId, @PathParam("promotionId") Integer promotionId) {
        try {
            promotionBean.addPromotionToUser(userId, promotionId);
            return Response.ok().build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{userId}/{promotionId}/verify")
    public Response verifyPromotion(@PathParam("userId") Integer userId, @PathParam("promotionId") Integer promotionId, TransactionDto transactionDto) {
        try {
            Boolean result = promotionBean.verifyPromotion(userId, promotionId, transactionDto);
            return Response.ok(result).build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    public Response createPromotion(PromotionDto promotionDto) {
        try {
            PromotionDto result = promotionBean.createPromotion(promotionDto);
            return Response.ok(result).build();
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{userId}/{promotionId}")
    public Response applyPromotion(@PathParam("userId") Integer userId, @PathParam("promotionId") Integer promotionId) {
        try {
            return promotionBean.applyPromotion(userId, promotionId);
        }
        catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
