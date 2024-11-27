package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.NotificationModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsResources {


    @GET
    public Response getAllNotifications(){

    }

    @POST
    public void addNotification(NotificationModel notification){

    }

    @PUT
    @Path("/{id}")
    public void readNotification(@PathParam("id") int id){

    }
}
