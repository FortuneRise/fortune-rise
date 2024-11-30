package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.NotificationModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsResource {


    @GET
    public Response getAllNotifications(){

    }



    @PUT
    @Path("/{id}")
    public void readNotification(@PathParam("id") int id){

    }
}
