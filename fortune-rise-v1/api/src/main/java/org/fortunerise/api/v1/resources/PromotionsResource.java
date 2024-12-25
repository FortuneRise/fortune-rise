package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.PromotionModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionsResource {

    @GET
    public Response getPromotions(){
        return Response.ok(promotions).build();
    }

    @POST
    public void addPromotion(PromotionModel promotion){
        promotions.add(promotion);
        return;

    }


}
