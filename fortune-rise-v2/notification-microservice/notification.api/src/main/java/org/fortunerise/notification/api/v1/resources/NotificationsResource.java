package org.fortunerise.notification.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.fortunerise.notification.services.NotificationBean;
import org.fortunerise.notification.services.NotificationDto;

import com.kumuluz.ee.rest.beans.QueryParameters;

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
@Tag(name = "Notifications API", description = "Endpoints for managing notifications.")
public class NotificationsResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private NotificationBean notificationBean;

    @GET
    @Path("/{userId}")
    @Operation(summary = "Get all notifications", description = "Retrieve all notifications for a specific user.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "List of notifications retrieved successfully.",
                    content = @Content(schema = @Schema(implementation = NotificationDto.class, type = SchemaType.ARRAY))),
            @APIResponse(responseCode = "404", description = "No notifications found for the user."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response getAllNotifications(
            @Parameter(name = "userId", description = "The ID of the user.", required = true)
            @PathParam("userId") Integer userId) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        try {
            List<NotificationDto> notificationList = notificationBean.getAllNotifiacationsByUsrId(userId, queryParameters);
            return Response.ok(notificationList).build();
        }
        catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path("/{userId}/{notificationId}")
    @Operation(summary = "Mark notification as read", description = "Marks a specific notification as read for a user.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Notification marked as read.",
                    content = @Content(schema = @Schema(implementation = NotificationDto.class))),
            @APIResponse(responseCode = "404", description = "Notification or user not found."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response readNotification(
            @Parameter(name = "userId", description = "The ID of the user.", required = true)
            @PathParam("userId") Integer userId,
            @Parameter(name = "notificationId", description = "The ID of the notification.", required = true)
            @PathParam("notificationId") Integer notificationId) {
        try {
            NotificationDto notification = notificationBean.readNotificationDto(notificationId, userId);
            return Response.ok(notification).build();
        }
        catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{userId}")
    @Operation(summary = "Add a notification", description = "Creates a new notification for a specific user.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Notification created successfully.",
                    content = @Content(schema = @Schema(implementation = NotificationDto.class))),
            @APIResponse(responseCode = "400", description = "Invalid input data."),
            @APIResponse(responseCode = "404", description = "User not found."),
            @APIResponse(responseCode = "500", description = "Internal server error.")
    })
    public Response addNotificationToUser(
            @Parameter(name = "userId", description = "The ID of the user.", required = true)
            @PathParam("userId") Integer userId,
            @Parameter(name = "NotificationDto", description = "Details of the notification to be created.", required = true)
            NotificationDto notificationDto) {
        try {
            NotificationDto notification = notificationBean.addNotification(notificationDto, userId);
            return Response.status(Response.Status.CREATED).entity(notification).build();
        }
        catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
        catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
