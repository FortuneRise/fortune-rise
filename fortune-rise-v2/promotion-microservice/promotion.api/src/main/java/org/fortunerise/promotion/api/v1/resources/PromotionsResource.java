package org.fortunerise.promotion.api.v1.resources;


import org.fortunerise.promotion.services.PromotionBean;
import org.fortunerise.promotion.services.PromotionDto;
import org.fortunerise.promotion.services.TransactionDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionsResource {

    @Inject
    PromotionBean promotionBean;

    @GET
    public Response getPromotions() {
        try {
            List<PromotionDto> result = promotionBean.getPromotionDtos();
            return Response.ok(result).build();
        } catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{userId}")
    public Response getPromotionsByUserId(@PathParam("userId") Integer userId, @QueryParam("trigger_scenario") String triggerScenarioParam) {
        try {
            List<PromotionDto> result = switch (triggerScenarioParam) {
                case "all" -> promotionBean.getPromotionDtosByUserId(userId, PromotionBean.TriggerScenario.ALL);
                case "deposit" -> promotionBean.getPromotionDtosByUserId(userId, PromotionBean.TriggerScenario.DEPOSIT);
                case "bet" -> promotionBean.getPromotionDtosByUserId(userId, PromotionBean.TriggerScenario.BET);
                default -> throw new IllegalArgumentException("Invalid trigger scenario: " + triggerScenarioParam);
            };

            return Response.ok(result).build();
        } catch (IllegalArgumentException | BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{userId}")
    public Response addPromotionToUser(@PathParam("userId") Integer userId, @QueryParam("promotion_id") Integer promotionId) {
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
    @Path("/verify")
    public Response verifyPromotion(@QueryParam("user_id") Integer userId, @QueryParam("promotion_id") Integer promotionId, TransactionDto transactionDto) {
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
    @Path("/{userId}")
    public Response applyPromotion(@PathParam("userId") Integer userId, @QueryParam("promotion_id") Integer promotionId) {
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
