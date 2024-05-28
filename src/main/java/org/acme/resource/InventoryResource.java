package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.service.InventoryService;

@Path("/inventory")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InventoryResource {

    @Inject
    InventoryService inventoryService;

    @PUT
    @Path("/{storeId}/{productId}")
    @Transactional
    public Response updateInventory(@PathParam("storeId") Long storeId, @PathParam("productId") Long productId, int quantity) {
        inventoryService.updateInventory(storeId, productId, quantity);
        return Response.ok().build();
    }
}
