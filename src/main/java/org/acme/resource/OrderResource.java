package org.acme.resource;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Order;
import org.acme.service.OrderService;

import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @POST
    @Transactional
    public Response placeOrder(Order order) {
        orderService.placeOrder(order);
        return Response.ok().entity(order).build();
    }

    @GET
    @Path("/{customerId}")
    public List<Order> getOrderHistory(@PathParam("customerId") Long customerId) {
        return orderService.getOrderHistory(customerId);
    }
}
