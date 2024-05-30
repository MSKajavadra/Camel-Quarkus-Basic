package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.Customer;
import org.acme.entity.Delivery;
import org.acme.service.CustomerService;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    @Inject
    CustomerService customerService;

    @POST
    @Transactional
    public Response registerCustomer(Customer customer) {
        customerService.registerCustomer(customer);
        return Response.ok().entity(customer).build();
    }
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            return Response.ok().entity(customer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found for order ID: " + id).build();
        }
    }
}
