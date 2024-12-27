package org.fortunerise.notification.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.fortunerise.notification.services.NotificationBean;
import org.fortunerise.notification.services.NotificationDto;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/notifications")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class NotificationsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private NotificationBean notificationBean;

    @GET
    @Path("/{userId}")
    public Response getAllNotifications(@PathParam("userId") Integer userId){
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            List<NotificationDto> notificationList = notificationBean.getAllNotifiacationsByUsrId(userId, queryParameters);
            return Response.ok(notificationList).build();
        }
        catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PUT
    @Path("/{userId}/{notificationId}")
    public Response readNotification(@PathParam("userId") Integer userId, @PathParam("notificationId") Integer notificationId) {
        try {
            NotificationDto notification = notificationBean.readNotificationDto(notificationId, userId);
            return Response.ok(notification).build();
        }
        catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @POST
    @Path("/{userId}")
    public Response addNotificationToUser(@PathParam("userId") Integer userId, NotificationDto notificationDto){
        try {
            NotificationDto notification = notificationBean.addNotification(notificationDto, userId);
            return Response.ok(notification).build();
        }
        catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
