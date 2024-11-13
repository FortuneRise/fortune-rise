package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.Promotion;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PromotionsResource {

    private static List<Promotion> promotions;

    @GET
    public Response getPromotions(){
        return Response.ok(promotions).build();
    }

    @POST
    public void addPromotion(Promotion promtion){
        promotions.add(promtion);
        return;

    }


}
