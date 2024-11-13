package org.fortunerise.api.v1.resources;

import org.fortunerise.api.v1.models.Notification;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsResources {

    private static List<Notification> notifications;


    @GET
    public Response getAllNotifications(){
        return Response.ok(notifications).build();
    }

    @POST
    public void addNotification(Notification notification){
        notifications.add(notification);
        return;
    }

    @PUT
    @Path("/{ntfid}")
    public void readNotification(@PathParam("ntfid") int id){
        for(Notification notification : notifications){
            if (notification.getID() == id) notification.setRead(true);
        }
    }
}
