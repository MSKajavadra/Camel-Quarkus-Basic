package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Delivery;
import org.acme.service.DeliveryService;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryResource {

    @Inject
    DeliveryService deliveryService;

    @GET
    @Path("/{orderId}")
    public Response getDeliveryDetails(@PathParam("orderId") String orderId) {
        Delivery delivery = deliveryService.getDeliveryDetails(orderId);
        if (delivery != null) {
            return Response.ok().entity(delivery).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Delivery details not found for order ID: " + orderId).build();
        }
    }
    @POST
    public Response createDelivery(Delivery delivery) {
        deliveryService.createDelivery(delivery);
        return Response.ok().entity("Delivery created successfully").build();
    }
}