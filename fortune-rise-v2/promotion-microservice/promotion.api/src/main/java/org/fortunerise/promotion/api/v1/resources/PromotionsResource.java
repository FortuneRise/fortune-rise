package org.fortunerise.promotion.api.v1.resources;


import org.fortunerise.promotion.services.PromotionBean;
import org.fortunerise.promotion.services.PromotionDto;

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
    @Path("/{userId}")
    public Response getPromotionsByUserId(@PathParam("userId") Integer userId, @QueryParam("trigger_scenario") String triggerScenarioParam) {
        try {
            List<PromotionDto> result = switch (triggerScenarioParam) {
                case "all" -> promotionBean.getPromotionsByUserId(userId, PromotionBean.TriggerScenario.ALL);
                case "deposit" -> promotionBean.getPromotionsByUserId(userId, PromotionBean.TriggerScenario.DEPOSIT);
                case "bet" -> promotionBean.getPromotionsByUserId(userId, PromotionBean.TriggerScenario.BET);
                default -> throw new IllegalArgumentException("Invalid trigger scenario: " + triggerScenarioParam);
            };

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


}
